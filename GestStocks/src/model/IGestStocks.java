package model;

import exceptions.*;

import java.util.List;
import java.util.Map;

public interface IGestStocks {

    void registarPalete(String codPalete);

    Map<String, Integer> localizacoes(List<String> paletes);

    Map<String, Integer> localizacoesRobots(List<String> robots);

    Robot getRobot(int locPalete);

    void forneceRotas(Robot robot, String codPalete, int locPalete, int locDestino);

    void transportarPalete(String codPalete) throws paleteException, robotException, prateleiraException;

    void paleteRecolhida(Robot robot, int locPalete);

    void paleteEntregue(Robot robot, Palete palete, int locDestino) ;

    void updateLocalizacao(Robot robot, Palete palete);

    List<String> paletesKeySet();

    List<String> robotsKeySet();

    List<String> paletesArmazenadas();

    void adicionaUtilizador(Utilizador u);

    boolean existeUtilizador(String username);

    boolean validaUser(String username,String pass);

    boolean haUsers();

    boolean haPaletes();

    boolean haRobots();

    boolean existePalete(String codPalete);

    void addThings();

    void requisicao(String requisitada);

    void registaPaletes();
}
