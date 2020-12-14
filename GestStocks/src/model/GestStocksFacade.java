package model;


import data.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestStocksFacade {
    //queue of paletes to read
    //private Map<String, Utilizador> users;
    private Map<String, Utilizador> users; //melhor com id ou string?????
    private Map<String, Robot> robots;
    private Map<String, Palete> paletes;
    private Map<Integer, Localizacao> mapa;
    //private List<Requisicao> requisicoes;
    // ...


    public GestStocksFacade(boolean cleanData, boolean newmap) {
        DAOconnection.createDB();
        this.users = UtilizadorDAO.getInstance();
        this.mapa = LocalizacaoDAO.getInstance();
        this.robots = RobotDAO.getInstance();
        this.paletes = PaleteDAO.getInstance();
        if(newmap) createMapa();
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

        this.mapa.put(11, new Localizacao(10, 2, false)); //zona de entregas

        // adicionar cantos
        this.mapa.put(12, new Localizacao(3, 0, false)); //Canto 1
        this.mapa.put(13, new Localizacao(3, 5, false)); //Canto 1

    }


    public void addThings(){
        this.users.put("", new Utilizador("ana", "anaaaa", "dfguijhghj"));
        this.users.put("", new Utilizador("lol", "lol@lol", "loooool"));
        this.users.put("", new Utilizador("sdf", "sd@asd", "asdfcds"));


        System.out.println(this.users.size());
    }


    void registarPalete(String codPalete){
        new Palete (codPalete, this.mapa.get(1));
    }

    Map<String, Localizacao> localizacoes(List<String> paletes){
        Map<String, Localizacao> localizacoes = new HashMap<>();
        for(Palete p: this.paletes.values()){
            localizacoes.put(p.getCodPalete(),p.getLocalizacao());
        }
        return localizacoes;
    }


    Robot getRobot(Localizacao locPalete){
        double d=1000;
        String rs= this.robots.get(1).getIdRobot();
        for(Robot r: this.robots.values()){
            double dc=r.getLocalizacao().distancia(locPalete);
            if(dc<d) {
                d = dc;
                rs = r.getIdRobot();
            }
        }
        return new Robot(rs);
    }



}
