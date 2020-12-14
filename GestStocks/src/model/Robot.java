package model;

import java.util.List;

public class Robot {
    private String idRobot;
    private int estado; // 0-livre 1-ocupado
    private int localizacao;
    private List<Integer> percurso;
    private double distancia;



    public Robot(String idRobot){
        this.idRobot = idRobot;
        this.estado= 0;
    }

    public String getIdRobot() {
        return this.idRobot;
    }

    public int getEstado() {
        return this.estado;
    }

    public int getLocalizacao() {
        return this.localizacao;
    }

    public List<Integer> getPercurso() {
        return this.percurso;
    }

    public double getDistancia() {
        return this.distancia;
    }
}
