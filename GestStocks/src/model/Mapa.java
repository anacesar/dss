package model;

import java.util.HashMap;
import java.util.Map;

public class Mapa {

    public final Map<Integer, Localizacao> mapa = new HashMap<>() {{
        put(0, new Localizacao(2, 0)); //zona de rececao

        put(1, new Localizacao(5, 0, false)); //prateleira 1 Corredor 1
        put(2, new Localizacao(6, 0, false)); //prateleira 2 Corredor 1
        put(3, new Localizacao(7, 0, false)); //prateleira 3 Corredor 1
        put(4, new Localizacao(8, 0, false)); //prateleira 4 Corredor 1
        put(5, new Localizacao(9, 0, false)); //prateleira 5 Corredor 1

        put(6, new Localizacao(5, 5, false)); //prateleira 6 Corredor 2
        put(7, new Localizacao(6, 5, false)); //prateleira 7 Corredor 2
        put(8, new Localizacao(7, 5, false)); //prateleira 8 Corredor 2
        put(9, new Localizacao(8, 5, false)); //prateleira 9 Corredor 2
        put(10, new Localizacao(9, 5, false)); //prateleira 10 Corredor 2

        put(11, new Localizacao(10, 2, false)); //zona de entregas

        // adicionar cantos
        put(12, new Localizacao(3, 0, false)); //Canto 1
        put(13, new Localizacao(3, 5, false)); //Canto 1

    }};

    public Map<Integer, Localizacao> getMapa() {
        return this.mapa;
    }

    int getDistancia(Mapa a, Mapa b){
        return 0;
    }

}
