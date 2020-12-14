package model;

import java.util.ArrayList;
import java.util.List;

public class Requisicao {
    private List<String> paletes;
    private List<String> paletes_disponiveis;
    private List<String> paletes_indisponiveis;

    public Requisicao (List<String> paletes){
        this.paletes=paletes;
        this.paletes_disponiveis= new ArrayList<>();
        this.paletes_indisponiveis= new ArrayList<>();
    }

    public List<String> getPaletes() {
        return this.paletes;
    }

    public void setPaletes(List<String> paletes) {
        this.paletes = paletes;
    }

    public List<String> getPaletes_disponiveis() {
        return this.paletes_disponiveis;
    }

    public void setPaletes_disponiveis(List<String> paletes_disponiveis) {
        this.paletes_disponiveis = paletes_disponiveis;
    }

    public void setPaletes_indisponiveis(List<String> paletes_indisponiveis) {
        this.paletes_indisponiveis = paletes_indisponiveis;
    }

    public List<String> getPaletes_indisponivies() {
        return this.paletes_indisponiveis;
    }

}
