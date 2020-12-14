package model;

import java.util.List;

public class Robot {
    private String idRobot;
    private int estado; // 0-livre 1-ocupado
    private Localizacao localizacao;
    private List<Localizacao> percurso;
    private double distancia;

    private Mapa mapa= new Mapa();

    public Robot(String idRobot){
        this.idRobot = idRobot;
        this.estado= 0;
        this.localizacao= mapa.getMapa().get(1);
    }



}
