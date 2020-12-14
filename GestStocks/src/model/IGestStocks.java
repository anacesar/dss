package model;

import java.util.List;
import java.util.Map;

public interface IGestStocks {

    void registarPalete(String codPalete);

    Map<String, Localizacao> localizacoes(List<String> paletes);

    Robot getRobot(Mapa locPalete);

    void forneceRotas(Robot robot, String codPalete, Localizacao locPalete, Localizacao locDestino);

    void transportarPalete(String codPalete);

    void paleteRecolhida(String codPalete);

    void paleteEntregue(String codPalete);

    void updateLocalizacao(String codPalete, Localizacao locDestino);


}
