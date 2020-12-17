package model;


import data.*;
import exceptions.*;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GestStocksFacade implements IGestStocks {
    private Map<String, Utilizador> users;
    private Map<String, Robot> robots;
    private Map<String, Palete> paletes;
    private Map<Integer, Localizacao> mapa;
    private Queue <String> queue;  //queue of paletes to read

    private Lock lock = new ReentrantLock();
    private Condition queueEmpty = lock.newCondition();
    private Condition noRobots = lock.newCondition();
    private Condition noPrateleiras = lock.newCondition();

    public GestStocksFacade(boolean cleanData, boolean newmap) {
        DAOconnection.createDB();
        this.users = UtilizadorDAO.getInstance();
        this.mapa = LocalizacaoDAO.getInstance();
        this.robots = RobotDAO.getInstance();
        this.paletes = PaleteDAO.getInstance();
        if(newmap) createMapa();
        this.queue = new ArrayDeque<>();
        if(cleanData) this.clearDB();

        /* thread responsável por atribuir transporte */
        new Thread(() -> {
            while(true) {
                String codPalete = null;
                try {
                    lock.lock();
                    while(this.queue.isEmpty()) this.queueEmpty.await();
                    //invoca necessidade de transporte
                    codPalete = queue.poll();
                    transportarPalete(codPalete);
                    System.out.println("transport ...");
                }catch(paleteException | InterruptedException e) {
                    System.out.println("transport " +codPalete + " couldnt happen...");
                }catch(robotException robot){
                    this.queue.add(codPalete);
                    try {
                        this.noRobots.await();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }catch(prateleiraException prateleira){
                    this.queue.add(codPalete);
                    try {
                        this.noPrateleiras.await();
                    }catch(InterruptedException e) {
                    }
                }finally {lock.unlock();}
            }
        }).start();
    }


    /**
     * Método que limpa as tabelas dos Utilizadores, Robots e Paletes
     */
    public void clearDB() {
        UtilizadorDAO.clearUserTable();
        RobotDAO.clearRobotTable();
        PaleteDAO.clearPaleteTable();
    }

    /**
     * Método que insere no Map<Integer, Localizacao> mapa
     * os nodos do mapa e as suas respetivas coordenadas
     */
    public void createMapa() {
        this.mapa.put(0, new Localizacao(2, 0)); // Zona de Receção

        this.mapa.put(1, new Localizacao(5, 0, false)); //prateleira 1 Corredor 1
        this.mapa.put(2, new Localizacao(6, 0, false)); //prateleira 2 Corredor 1
        this.mapa.put(3, new Localizacao(7, 0, false)); //prateleira 3 Corredor 1
        this.mapa.put(4, new Localizacao(8, 0, false)); //prateleira 4 Corredor 1
        this.mapa.put(5, new Localizacao(9, 0, false)); //prateleira 5 Corredor 1

        this.mapa.put(6, new Localizacao(5, 5, false)); //prateleira 6  Corredor 2
        this.mapa.put(7, new Localizacao(6, 5, false)); //prateleira 7  Corredor 2
        this.mapa.put(8, new Localizacao(7, 5, false)); //prateleira 8  Corredor 2
        this.mapa.put(9, new Localizacao(8, 5, false)); //prateleira 9  Corredor 2
        this.mapa.put(10, new Localizacao(9, 5, false));//prateleira 10 Corredor 2

        /* Cantos para passar de um corredor para o outro */
        this.mapa.put(11, new Localizacao(3, 0, false)); //Canto 1
        this.mapa.put(12, new Localizacao(3, 5, false)); //Canto 2

    }

    /**
     * Método que devolve a Localização consoante o id do nodo dado
     *
     * @param idNodo do mapa
     * @return Localização
     */
    public Localizacao getMapa(int idNodo) {
        return this.mapa.get(idNodo);
    }

    /**
     *
     * Método que povoa o nosso sistema com robots, paletes e gestores
     *
     * De modo a conseguirmos testar se o processo de transporte de diversas paletes com diversos robots
     * Adicionamos também alguns registaPalete(codPalete)
     * 
     */
    public void addThings() {

        this.users.put("", new Utilizador("u1", "u1@email.com", "u1"));

        /*
        this.users.put("", new Utilizador("u2", "u2@email.com", "u2"));
        this.users.put("", new Utilizador("u3", "u2@email.com", "u3"));
        this.users.put("", new Utilizador("u4", "u2@email.com", "u4"));
        */

        this.robots.put("", new Robot("r1", 0, 6));
        this.robots.put("", new Robot("r2", 0, 3));
        this.robots.put("", new Robot("r3", 0, 5));
        this.robots.put("", new Robot("r4", 0, 10));
        this.robots.put("", new Robot("r5", 0, 6));

        this.paletes.put("", new Palete("p1", 2));
        this.paletes.put("", new Palete("p2", 3));
        this.paletes.put("", new Palete("p3", 9));
        this.paletes.put("", new Palete("p4", 4));
        this.paletes.put("", new Palete("p5", 1));

        /* testar armazenamento de uma palete
        registarPalete("p6");
        registarPalete("p7");
        registarPalete("p8");
        registarPalete("p9");
        registarPalete("p10");
        registarPalete("p11");
        */
    }


    public void registarPalete(String codPalete) {
        // add de uma palete registada na zona de receção (nodo 0 do mapa)
        this.paletes.put("", new Palete(codPalete, 0));
        this.queue.add(codPalete);
        lock.lock();
        this.queueEmpty.signal();
        lock.unlock();
    }

    public Map<String, Integer> localizacoes(List<String> paletes) {
        Map<String, Integer> localizacoes = new HashMap<>();
        for(String codPalete : paletes) {
            Palete p = this.paletes.get(codPalete);
            if(p == null) localizacoes.put(codPalete, -1); //palete nao esta no armazem
            else localizacoes.put(codPalete, p.getLocalizacao());
        }
        return localizacoes;
    }


    private double distanciaPalete(int loc, int destino) {
        double distancia = 0;
        if(loc < 6 && destino >= 6 || loc >= 6 && destino < 6) { //nao estao no mesmo corredor
            distancia += 5;
        }
        distancia += getMapa(loc).distancia(getMapa(destino));

        return distancia;
    }

    public Robot getRobot(int locPalete) {
        double d = 1000;
        Robot robot = null;
        for(Robot r : this.robots.values()) {
            if(r.getEstado() == 0){ //robot esta livre
                double dc = distanciaPalete(r.getLocalizacao(), locPalete);
                System.out.println("robot: " + r.getIdRobot() + "   d= " + dc);
                if(dc < d) {
                    d = dc;
                    robot = r;
                }
            }
        }
        return robot;
    }

    public List<Integer> checkLocalizacao(int loc, int destino) {
        List<Integer> percurso = new ArrayList<>();
        if(loc < 6 && destino >= 6 || loc >= 6 && destino < 6) { //nao estao no mesmo corredor
            if(loc < 6) {//corredor 1 -- canto 1 --> canto 2 --> destino
                percurso.add(11);
                percurso.add(12);
            } else {//corredor 2 -- canto 2 --> canto 1 --> destino
                percurso.add(12);
                percurso.add(11);
            }
        }
        percurso.add(destino);

        return percurso;
    }

    @Override
    public void forneceRotas(Robot robot, String codPalete, int locPalete, int locDestino) {
        int locRobot = robot.getLocalizacao();

        robot.setEstado(1); //robot ocupado
        robot.setCodPalete(codPalete); //atribuir codPalete
        robot.setRobotPalete(checkLocalizacao(locRobot, locPalete)); //atribuir percurso robot -> palete
        robot.setPaleteDestino(checkLocalizacao(locPalete, locDestino));//atribuir percurso palete -> destino

        this.robots.put(robot.getIdRobot(), robot); //atualiza o estado do robot
    }

    private double distanciaPercurso(Robot robot, boolean toPalete) {
        Localizacao origem, destino;
        double distancia = 0;
        List<Integer> percurso;

        if(toPalete) {/*transporte robot -> palete*/
            percurso = robot.getRobotPalete();
        } else {/*transporte palete(robot na loc da palete) -> destino*/
            percurso = robot.getPaleteDestino();
        }

        int size = percurso.size();
        origem = getMapa(robot.getLocalizacao()); //localizacao de partida e sempre a localizacao do robot
        destino = getMapa(percurso.get(size - 1));

        if(size > 1) distancia += 5; //e necessario passar nos cantos do mapa
        distancia += origem.distancia(destino);

        return distancia;
    }

    public int getPrateleiraLivre() {
        for(int i = 1; i < 11; i++)
            if(!getMapa(i).isOcupado()) return i;

        return -1;  //nao ha prateleiras livres
    }

    @Override
    public void transportarPalete(String codPalete) throws paleteException, robotException, prateleiraException {
        Palete palete = this.paletes.get(codPalete);//verificar palete != null
        if(palete == null) throw new paleteException();

        Robot robot = getRobot(palete.getLocalizacao()); //verificar robot != null
        if(robot == null)//nao existe robot disponivel para o transporte
            throw new robotException("Não existe nenhum robot disponível para transporte");

        System.out.println(robot.getIdRobot());

        int destino = getPrateleiraLivre(); //e se não houver prateleiras livres?
        if(destino == -1) throw new prateleiraException();

        Localizacao l = getMapa(destino);
        l.setOcupado(true);//prateleira ocupada
        this.mapa.put(destino, l); //atualizar base de dados com prateleira ocupada


        forneceRotas(robot, palete.getCodPalete(), palete.getLocalizacao(), destino);

        /* criar nova thread que simula o transporte dos robots - independente ao funcionamento do sistema */
        new Thread(() ->{
            double time_robotPalete = distanciaPercurso(robot, true) / 0.003; //velocidade media 10km/h
            double time_paleteDestino = distanciaPercurso(robot, false) / 0.003;

            try {
                System.out.println("before transport: " + robot.toString());
                Thread.sleep((long) time_robotPalete); //simulacao robot -> palete
                paleteRecolhida(robot, palete.getLocalizacao());
                System.out.println("in transport(recolha): " + robot.toString());
                Thread.sleep((long) time_paleteDestino); //simulacao de palete -> destino
                paleteEntregue(robot, palete, destino);
                System.out.println("after transport: " + robot.toString());

            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void paleteRecolhida(Robot robot, int locPalete) {
        robot.setLocalizacao(locPalete);
        if(locPalete != 1) {
            Localizacao locP = getMapa(locPalete);
            locP.setOcupado(false);//localização livre
            this.mapa.put(locPalete, locP);
            /*sinalizar que prateleira ficou livre -> caso houvesse requisicoes,
             quando prateleira ficasse livre notifica pedidos de armazenamento pendentes por falta de espaco no armazem */
            lock.lock();
            this.noPrateleiras.signal();
            lock.unlock();
        }
    }


    @Override
    public void paleteEntregue(Robot robot, Palete palete, int locDestino) {
        robot.setLocalizacao(locDestino); // robot na localização final
        robot.setEstado(0);// robot livre
        robot.clearDataTransporte(); // limpa percurso e codPalete a entregar
        palete.setLocalizacao(locDestino);//atualiza localizacao da palete para destino
        updateLocalizacao(robot, palete);
        //sinalizar que robot ficou livre -> caso haja pedidos de tranporte pendentes por falta de robots livres
        lock.lock();
        this.noRobots.signal();
        lock.unlock();
    }

    @Override
    public void updateLocalizacao(Robot robot, Palete palete) {
        //update na base de dados
        this.robots.put("", robot); //atualizar informacoes do robot na bd
        this.paletes.put(robot.getCodPalete(), palete); // atualizar localização palete

    }

    public void adicionaUtilizador(Utilizador u) {
        this.users.put(u.getUsername(), u);
    }

    public boolean existeUtilizador(String username) {
        return this.users.containsKey(username);
    }

    public boolean validaUser(String username, String pass) {
        return this.users.get(username).getPassword().equals(pass);
    }

    public boolean haUsers(){
        return !this.users.isEmpty();
    }

    public boolean haPaletes(){
        return !this.paletes.isEmpty();
    }

    public boolean haRobots(){
        return !this.robots.isEmpty();
    }

    public boolean existePalete(String codPalete) {
        return this.paletes.containsKey(codPalete);
    }

    public Map<Integer, String> paletes_mapa(){
        Map<Integer, String> paletes = new HashMap<>();
        //percorrer as paletes e ver as localizacoes
        this.paletes.values().forEach(palete -> paletes.put(palete.getLocalizacao(), palete.getCodPalete()));
        return paletes;
    }

    public List<String> paletesKeySet(){
        return new ArrayList<>(this.paletes.keySet());
    }
}

