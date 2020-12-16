package model;

import exceptions.*;

import java.util.List;
import java.util.Map;

public interface IGestStocks {

    void registarPalete(String codPalete);

    Map<String, Integer> localizacoes(List<String> paletes);

    Robot getRobot(int locPalete);

    void forneceRotas(Robot robot, String codPalete, int locPalete, int locDestino);

    void transportarPalete(String codPalete) throws paleteException, robotException, prateleiraException;

    void paleteRecolhida(Robot robot, int locPalete);

    void paleteEntregue(Robot robot, Palete palete, int locDestino) ;

    void updateLocalizacao(Robot robot, Palete palete, Localizacao locDestino);


}
