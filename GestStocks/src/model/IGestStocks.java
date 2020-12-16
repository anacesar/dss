package model;

import java.util.List;
import java.util.Map;

public interface IGestStocks {

    void registarPalete(String codPalete);

    Map<String, Integer> localizacoes(List<String> paletes);

    Robot getRobot(int locPalete);

    void forneceRotas(Robot robot, String codPalete, int locPalete, int locDestino);

    void transportarPalete(String codPalete);

    void paleteRecolhida(Robot robot, int locPalete);

    void paleteEntregue(Robot robot);

    void updateLocalizacao(Robot robot);


}
