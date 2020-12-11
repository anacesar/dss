package model;

import java.util.HashMap;
import java.util.Map;

public class Mapa {

    public final Map<Integer, Localizacao> mapa = new HashMap<>() {{
        put(0, new Localizacao(2, 3)); //zona de rececao
        put(1, new Localizacao(2, 2, false)); //prateleira 1
        put(2, new Localizacao(2, 2, false)); //prateleira 2
    }};




}
