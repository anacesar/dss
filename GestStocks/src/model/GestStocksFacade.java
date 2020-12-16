package model;


import data.*;
import exceptions.*;

import java.util.*;

public class GestStocksFacade implements IGestStocks{
    private Map<String, Utilizador> users;
    private Map<String, Robot> robots;
    private Map<String, Palete> paletes;
    private Map<Integer, Localizacao> mapa;
    //private List<Requisicao> requisicoes;

    //queue of paletes to read
    Queue<String> queue;
    //queue of paletes ready to transport
    List<String> transporte;


    public GestStocksFacade(boolean cleanData, boolean newmap) {
        DAOconnection.createDB();
        this.users = UtilizadorDAO.getInstance();
        this.mapa = LocalizacaoDAO.getInstance();
        this.robots = RobotDAO.getInstance();
        this.paletes = PaleteDAO.getInstance();
        if(newmap) createMapa();
        this.queue = new ArrayDeque<>();
        this.transporte = new ArrayList<>();
        if(cleanData) this.clearDB();
    }


    public void clearDB(){
        UtilizadorDAO.clearUserTable();
    }

    public void createMapa(){
        this.mapa.put(0, new Localizacao(2, 0)); //zona de rececao

        this.mapa.put(1, new Localizacao(5, 0, false)); //prateleira 1 Corredor 1
        this.mapa.put(2, new Localizacao(6, 0, false)); //prateleira 2 Corredor 1
        this.mapa.put(3, new Localizacao(7, 0, false)); //prateleira 3 Corredor 1
        this.mapa.put(4, new Localizacao(8, 0, false)); //prateleira 4 Corredor 1
        this.mapa.put(5, new Localizacao(9, 0, false)); //prateleira 5 Corredor 1

        this.mapa.put(6, new Localizacao(5, 5, false)); //prateleira 6 Corredor 2
        this.mapa.put(7, new Localizacao(6, 5, false)); //prateleira 7 Corredor 2
        this.mapa.put(8, new Localizacao(7, 5, false)); //prateleira 8 Corredor 2
        this.mapa.put(9, new Localizacao(8, 5, false)); //prateleira 9 Corredor 2
        this.mapa.put(10, new Localizacao(9, 5, false)); //prateleira 10 Corredor 2

        // adicionar cantos
        this.mapa.put(11, new Localizacao(3, 0, false)); //Canto 1
        this.mapa.put(12, new Localizacao(3, 5, false)); //Canto 2

        //this.mapa.put(13, new Localizacao(10, 2, false)); //zona de entregas
    }

    public Localizacao getMapa(int idNodo){return this.mapa.get(idNodo);}


    public void addThings(){
        /*
        this.users.put("", new Utilizador("ana", "anaaaa", "dfguijhghj"));
        this.users.put("", new Utilizador("lol", "lol@lol", "loooool"));
        this.users.put("", new Utilizador("sdf", "sd@asd", "asdfcds"));


        //System.out.println(this.users.get("shdj").getPassword()); feito - falta apanhar as excecoes
        //System.out.println(this.users.size());

        this.paletes.put("", new Palete("1234", 2));
        this.robots.put("", new Robot("r1", 0, 6));

        this.robots.put("", new Robot("r2", 0, 3));
        this.robots.put("", new Robot("r3", 0, 5));
        this.robots.put("", new Robot("r4", 0, 12));
        this.robots.put("", new Robot("r5", 1, 6));*

        //System.out.println(getRobot(getMapa(8)).getIdRobot());


         */
        //registarPalete("34567");

        this.paletes.put("", new Palete("45", 5));
        this.paletes.put("", new Palete("3", 12));
        /*

        List<String> loc = new ArrayList<String>();
        loc.add("1234"); loc.add("34567");loc.add("34543");loc.add("3");

        localizacoes(loc).forEach((k, v)-> System.out.println("palete: " + k + "\nlocalizacao : " +v));


        /* simulacao de uma palete a chegar ao armazem*/
        this.robots.put("", new Robot("r3", 0, 8));
        this.robots.put("", new Robot("r5", 0, 10));

        registarPalete("8239");

        //descobrir palete na zona de rececao
        String codPalete = this.queue.poll();


        try {
            transportarPalete(codPalete);
        } catch(paleteException | robotException | prateleiraException e) {
            e.printStackTrace();
        }

    }


    public void registarPalete(String codPalete){
        // add de uma palete registada na zona de receção (nodo 0 do mapa)
        this.paletes.put("", new Palete (codPalete,0));
        this.queue.add(codPalete);
    }

    public Map<String, Integer> localizacoes(List<String> paletes){
        Map<String, Integer> localizacoes = new HashMap<>();
        for(String codPalete : paletes){
            Palete p = this.paletes.get(codPalete);
            if(p == null) localizacoes.put(codPalete, -1); //palete nao esta no armazem
            else localizacoes.put(codPalete, p.getLocalizacao());
        }
        return localizacoes;
    }


    private double distanciaPalete(int loc, int destino){
        double distancia = 0;
        if(loc < 6 && destino >=6 || loc>=6 && destino<6){ //nao estao no mesmo corredor
            distancia += 5;
        }
        distancia += getMapa(loc).distancia(getMapa(destino));

        return distancia;
    }

    public Robot getRobot(int locPalete){
        double d=1000;
        Robot robot = null;
        for(Robot r: this.robots.values()){
            double dc=distanciaPalete(r.getLocalizacao(), locPalete);
            System.out.println("robot: " + r.getIdRobot() + "   d= " + dc);
            if(dc<d) {
                d = dc;
                robot= r;
            }
        }
        return robot;
    }

    public List<Integer> checkLocalizacao(int loc, int destino){
        List<Integer> percurso = new ArrayList<>();
        if(loc < 6 && destino >=6 || loc>=6 && destino<6){ //nao estao no mesmo corredor
            if(loc < 6){//corredor 1 -- canto 1 --> canto 2 --> destino
               percurso.add(11);
               percurso.add(12);
            }else{//corredor 2 -- canto 2 --> canto 1 --> destino
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

    private double distanciaPercurso(Robot robot, boolean toPalete){
        Localizacao origem,destino;
        double distancia = 0;
        List<Integer> percurso;

        if(toPalete){/*transporte robot -> palete*/
            percurso = robot.getRobotPalete();
        }else{/*transporte palete(robot na loc da palete) -> destino*/
            percurso = robot.getPaleteDestino();
        }

        int size = percurso.size();
        origem=getMapa(robot.getLocalizacao()); //localizacao de partida e sempre a localizacao do robot
        destino = getMapa(percurso.get(size-1));

        if (size>1) distancia += 5; //e necessario passar nos cantos do mapa
        distancia += origem.distancia(destino);

        return distancia;
    }

    public int getPrateleiraLivre(){
        for(int i= 1; i<11 ; i++)
            if(!getMapa(i).isOcupado()) return i;

        return -1;//nao ha prateleiras livres
    }

    @Override
    public void transportarPalete(String codPalete) throws paleteException, robotException, prateleiraException {
        Palete palete = this.paletes.get(codPalete);//verificar palete != null
        if(palete == null) throw new paleteException();

        Robot robot = getRobot(palete.getLocalizacao()); //verificar robot != null
        if(robot==null)//nao existe robot disponivel para o transporte
            throw new robotException("Não existe nenhum robot disponível para transporte");

        System.out.println(robot.getIdRobot());

        int destino = getPrateleiraLivre(); //e se não houver prateleiras livres?
        if(destino==-1) throw new prateleiraException();
        this.transporte.remove(robot.getCodPalete()); //tirar a palete do Estado: em transporte

        forneceRotas(robot, palete.getCodPalete(), palete.getLocalizacao(), destino);

        this.transporte.add(codPalete);

        //robot esta ocupado, sabe qual o percurso e qual a palete a transportar

        //criar nova thread que vai efetuar o transporte
        //worker da thread recebe o robot
        //percurso --> ver o nodo da palete e simular tempo de deslocacao

        double time_robotPalete = distanciaPercurso(robot, true) / 0.003; //velocidade media 10km/h
        double time_paleteDestino = distanciaPercurso(robot, false) / 0.003; ;

        try {
            System.out.println("before transport: " + robot.toString());
            Thread.sleep((long)time_robotPalete); //simulacao robot -> palete
            paleteRecolhida(robot, palete.getLocalizacao());
            System.out.println("in transport(recolha): " +robot.toString());
            Thread.sleep((long) time_paleteDestino); //simulacao de palete -> destino
            paleteEntregue(robot,palete, destino);
            System.out.println("after transport: " + robot.toString());
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paleteRecolhida(Robot robot, int locPalete) {
        robot.setLocalizacao(locPalete);
        if(locPalete!=1){
            Localizacao locP = getMapa(locPalete); locP.setOcupado(false);//localização livre
            this.mapa.put(locPalete, locP);
        }
    }


    @Override
    public void paleteEntregue(Robot robot, Palete palete, int locDestino) {
        robot.setLocalizacao(locDestino); // robot na localização final
        robot.setEstado(0);// robot livre
        robot.clearDataTransporte(); // limpa percurso e codPalete a entregar
        palete.setLocalizacao(locDestino);//atualiza localizacao da palete para destino
        Localizacao l = getMapa(locDestino); l.setOcupado(true);//prateleira ocupada
        updateLocalizacao(robot, palete, l);
    }

    @Override
    public void updateLocalizacao(Robot robot, Palete palete,  Localizacao locDestino) {
        //update na base de dados
        this.robots.put("", robot); //atualizar informacoes do robot na bd
        this.paletes.put(robot.getCodPalete(), palete); // atualizar localização palete
        this.mapa.put(palete.getLocalizacao(), locDestino);
    }

    public void adicionaUtilizador(Utilizador u) { this.users.put(u.getUsername(), u);}
    public boolean existeUtilizador(String username){return this.users.containsKey(username);}
    public boolean validaUser(String username,String pass){
        return this.users.get(username).getPassword().equals(pass);
    }

    public boolean haUsers() {
        return !this.users.isEmpty();
    }
    public boolean haPaletes(){return !this.paletes.isEmpty();}

    public boolean existePalete(String codPalete){
        return this.paletes.containsKey(codPalete);
    }

