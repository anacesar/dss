package model;

import java.util.List;

public class Robot {
    private String idRobot;
    private int estado; // 0-livre 1-ocupado
    private Localizacao localizacao;
    private List<Localizacao> percurso;
    //private double distancia;



    public Robot(String idRobot){
        this.idRobot = idRobot;
        this.estado= 0;
    }

    public Robot(String idRobot, int estado, double distancia) {
        this.idRobot = idRobot;
        this.estado = estado;
        // this.distancia = distancia;
    }

    public String getIdRobot() {
        return this.idRobot;
    }

    public int getEstado() {
        return this.estado;
    }

    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    public List<Localizacao> getPercurso() {
        return this.percurso;
    }

   /* public double getDistancia() {
        return this.distancia;
    }

    */
}
