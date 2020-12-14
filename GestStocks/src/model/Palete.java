package model;

public class Palete {
    private String codPalete;
    private Localizacao localizacao;


    public Palete(String codPalete){
        this.codPalete = codPalete;
    }

    public Palete(String codPalete, Localizacao loc){
        this.codPalete = codPalete;
        this.localizacao=loc;
    }

    public String getCodPalete() {
        return this.codPalete;
    }


    public Localizacao getLocalizacao() {
        return localizacao;
    }
}
