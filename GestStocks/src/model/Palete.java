package model;

public class Palete {
    private String codPalete;
    private Localizacao localizacao;


    public Palete(String codPalete, Localizacao localizacao){
        this.codPalete = codPalete;
        this.localizacao = localizacao;
    }

    public String getCodPalete() {
        return this.codPalete;
    }

    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

}
