package model;


import data.DAOconnection;
import data.RobotDAO;
import data.UtilizadorDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestStocksFacade {
    //queue of paletes to read
    //private Map<String, Utilizador> users;
    private Map<String, Utilizador> users; //melhor com id ou string?????
    private Map<String, Robot> robots;
    private Map<String, Palete> paletes;
    //private List<Requisicao> requisicoes;
    // ...

    private Mapa mapa= new Mapa();

    public GestStocksFacade(boolean cleanData) {
        DAOconnection.createDB();
        this.users = UtilizadorDAO.getInstance();
        this.robots = RobotDAO.getInstance();
        if(cleanData) this.clearDB();
    }


    public void clearDB(){
        this.users.clear();
    }


    public void addThings(){
        this.users.put("", new Utilizador("ana", "ana@ana", "aaaaa"));
        this.users.put("", new Utilizador("lol", "lol@lol", "loooool"));
        this.users.put("", new Utilizador("sdf", "sd@asd", "asdfcds"));
    }


    void registarPalete(String codPalete){
        new Palete (codPalete, mapa.getMapa().get(1));
    }

    Map<String, Localizacao> localizacoes(List<String> paletes){
        Map<String, Localizacao> localizacoes = new HashMap<>();
        for(Palete p: this.paletes.values()){
            localizacoes.put(p.getCodPalete(),p.getLocalizacao());
        }
        return localizacoes;
    }



}
