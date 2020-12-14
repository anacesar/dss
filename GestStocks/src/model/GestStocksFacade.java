package model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestStocksFacade {
    //queue of paletes to read
    //private Map<String, Utilizador> users;
    private Map<Integer, Utilizador> users; //melhor com id ou string?????
    private Map<String, Robot> robots;
    private Map<String, Palete> paletes;
    //private List<Requisicao> requisicoes;
    // ...

    private Mapa mapa= new Mapa();


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

    Robot getRobot(Mapa locPalete){

    }


}
