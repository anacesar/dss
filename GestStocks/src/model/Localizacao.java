package model;


public class Localizacao {
    private int x;
    private int y;
    private boolean ocupado; //caso de serem prateleiras saber se estao ocupadas
    //private boolean[] niveis = new boolean[5]; //n-> nr de niveis

    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
        this.ocupado = false;
    }

    public Localizacao(int x, int y, boolean ocupado) {
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

    public boolean isOcupado() {
        return this.ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
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
