package model;

public class Robot {
    private String idRobot;
    private int estado; // 0-livre 1-ocupado
    private Localizacao localizacao;
    private Localizacao destino;
    private double distancia;


    public Robot(){
        this.idRobot = "";
    }

}
