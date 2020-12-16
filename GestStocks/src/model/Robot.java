package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Robot {
    private String idRobot;
    private int estado; // 0-livre 1-ocupado
    private String codPalete;
    private int localizacao;
    private List<Integer> robotPalete = new ArrayList<>();
    private List<Integer> paleteDestino = new ArrayList<>();


    public Robot(String idRobot){
        this.idRobot = idRobot;
        this.estado= 0;
    }

    public Robot(String idRobot, int estado, int localizacao) {
        this.idRobot = idRobot;
        this.estado = estado;
        this.localizacao = localizacao;
    }

    public String getIdRobot() {
        return this.idRobot;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado){ this.estado=estado; }

    public String getCodPalete() { return this.codPalete; }

    public void setCodPalete(String codPalete) { this.codPalete = codPalete; }

    public int getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(int localizacao) {
        this.localizacao = localizacao;
    }

    public List<Integer> getRobotPalete() {
        return this.robotPalete;
    }

    public void setRobotPalete(List<Integer> robotPalete) {
        this.robotPalete = new ArrayList<>(robotPalete);
    }

    public List<Integer> getPaleteDestino() {
        return this.paleteDestino;
    }

    public void setPaleteDestino(List<Integer> paleteDestino) {
        this.paleteDestino = new ArrayList<>(paleteDestino);
    }

    public List<Integer> getPercurso() {
        List<Integer> percurso = new ArrayList<>(this.robotPalete);
        percurso.addAll(this.paleteDestino);

        return percurso;
    }

    public void clearDataTransporte(){
        this.robotPalete = new ArrayList<>();
        this.paleteDestino = new ArrayList<>();
        this.codPalete = null;
    }


    @Override
    public String toString() {
        return "Robot{" +
                "idRobot='" + idRobot + '\'' +
                ", estado=" + estado +
                ", codPalete='" + codPalete + '\'' +
                ", localizacao=" + localizacao +
                ", percurso=" + getPercurso() +
                '}';
    }
}
