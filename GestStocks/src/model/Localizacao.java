package model;


public class Localizacao {
    private int x;
    private int y;
    private boolean ocupado; //caso de serem prateleiras saber se estao ocupadas
    private boolean[] niveis = new boolean[5]; //n-> nr de niveis

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

    
}
