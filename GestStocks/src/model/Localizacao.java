package model;


public class Localizacao {
    private int x;
    private int y;
    private boolean ocupado; //caso de serem prateleiras saber se estao ocupadas
    //private boolean[] niveis = new boolean[5]; //n-> nr de niveis

    public Localizacao() {
        this.x = 0;
        this.y = 0;
        this.ocupado = false;
    }


    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
        this.ocupado = false;
    }

    public Localizacao(int x, int y, Boolean ocupado) {
        this.x = x;
        this.y = y;
        this.ocupado = ocupado;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Boolean getOcupado(){ return this.ocupado; }

    public boolean isOcupado() {
        return this.ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }


    public double distancia(Localizacao l) {
        return Math.sqrt(Math.pow(this.x - l.getX(), 2) +
                Math.pow(this.y - l.getY(), 2));
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "x=" + x +
                ", y=" + y +
                ", ocupado=" + ocupado +
                '}';
    }

    /*
    public boolean[] getNiveis() {
        return niveis;
    }

    public void setNiveis(boolean[] niveis) {
        this.niveis = niveis;
    }

     */
}
