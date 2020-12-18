package model;

public class Palete {
    private String codPalete;
    private int localizacao; //nodo do mapa

    public Palete(String codPalete, int loc){
        this.codPalete = codPalete;
        this.localizacao=loc;
    }

    public String getCodPalete() {
        return this.codPalete;
    }

    public int getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(int localizacao) {
        this.localizacao = localizacao;
    }
}
