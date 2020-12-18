package ui;

import model.GestStocksFacade;
import model.IGestStocks;
import model.Utilizador;

import java.util.*;

public class TextUI {
    private IGestStocks model;
    private Scanner scin;
    private ShowMapa showMapa;

    /**
     * Construtor.
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {
        this.model = new GestStocksFacade(true, true);
        scin = new Scanner(System.in);
        showMapa = new ShowMapa();
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Sistema de Gestão de Stocks!");
        this.menuInicial();
        System.out.println("Até breve...");
    }

    /**
     * Estado - Menu Principal
     */
    private void menuInicial() {
        Menu menu = new Menu(new String[]{
                "Registo",
                "Login"
        });

        // Registar pré-condições das transições´
          menu.setPreCondition(2, ()->this.model.haUsers());

        // Registar os handlers
        menu.setHandler(1, ()->registo());
        menu.setHandler(2, ()->login());

        menu.run();
    }

    /**
     * Menu Principal - Opção 1
     * Estado - Registo de um utilizador, neste caso um gestor
     */
    private void registo(){
        try{
            System.out.println("Username: ");
            String username = scin.nextLine();
            if(!this.model.existeUtilizador(username)) {
                System.out.println("Email: ");
                String email = scin.nextLine();
                System.out.println("Password: ");
                String pass = scin.nextLine();
                this.model.adicionaUtilizador(new Utilizador(username, email, pass));
                System.out.println("Utilizador adicionado");
            }else
                System.out.println("Esse username já existe!");

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu Principal - Opção 2
     * Estado - Login de um utilizador, neste caso um gestor
     */
    private void login(){
        try {
            System.out.println("Username: ");
            String username = scin.nextLine();
            if(this.model.existeUtilizador(username)) {
                System.out.println("Password: ");
                String pass = scin.nextLine();
                if(!this.model.validaUser(username,pass)) System.out.println("Username e Password não coincide!");
                else if(username.equals("admin")){
                    menuAdmin();
                }else menuUtilizador();
            }else {
                System.out.println("Não se encontra registado no sistema! \nPor favor faça o registo ou tente novamente.");
            }

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu Utilizador
     * Entra aqui após um login efetuado com sucesso
     * Estado - Login de um utilizador, neste caso um gestor
     */
    private void menuUtilizador() {
        Menu menu = new Menu(new String[]{
                "Consultar localizações",
                "Ver paletes no armazém",
                "Ver robots no armazém",
        });

        // Registar pré-condições das transições´
        menu.setPreCondition(1, ()->this.model.haPaletes());
        menu.setPreCondition(2, ()->this.model.haPaletes());
        menu.setPreCondition(3, ()->this.model.haRobots());



        // Registar os handlers
        menu.setHandler(1, ()->consultaLocalizacoes());
        menu.setHandler(2, ()->verPaletesMapa());
        menu.setHandler(3, ()->verRobotsMapa());

        menu.run();
    }

    /**
     * Menu Utilizador - Opção 1
     * Estado - Consultar localizações
     */
    private void consultaLocalizacoes(){
        try {
            List<String> locValidas = new ArrayList<>();
            List<String> locInvalidas = new ArrayList<>();

            System.out.println("Quantas localizações pretende consultar: ");
            int num = Integer.parseInt(scin.nextLine());
            System.out.println("Introduza os códigos das paletes:");

            for(int i=0; i<num; i++ ) {
                String cod = scin.nextLine();
                if(!this.model.existePalete(cod)) locInvalidas.add(cod);
                else locValidas.add(cod);
            }
            if (!locInvalidas.isEmpty()) System.out.println("\nOs códigos seguintes são inválidos: " + locInvalidas);
            if (!locValidas.isEmpty()) this.model.localizacoes(locValidas).forEach((k, v)-> {
                System.out.print("\nPalete: " + k);
                if(v == 0) System.out.println("\nLocalização : Zona de Receção");
                else System.out.println("\nLocalização : Prateleira " + v);
            });
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu Utilizador - Opção 2
     * Estado - Ver paletes no armazém
     */
    private void verPaletesMapa(){
        List<String> paletes_rececao = new ArrayList<>();
        Map<Integer, String> res = new HashMap<>();

        Map<String, Integer> locs = this.model.localizacoes(this.model.paletesKeySet());

        locs.entrySet().stream().forEach((map -> {
            if(map.getValue() == 0) paletes_rececao.add(map.getKey());
            else res.put(map.getValue(), map.getKey());
        }));

        this.showMapa.showThingsMapa(res);

        System.out.println("\n\nSelecione 1 para ver paletes em zona de receção (Pressione outra para sair)");
        try{
            if(Integer.parseInt(scin.nextLine()) ==1) this.showMapa.verZonaRececao(paletes_rececao);
        }catch(NumberFormatException e){}
    }

    /**
     * Menu Utilizador - Opção 3
     * Estado - Ver robots no armazém
     */
    private void verRobotsMapa(){
        List<String> robots_rececao = new ArrayList<>();
        Map<Integer, String> res = new HashMap<>();

        Map<String, Integer> locs = this.model.localizacoesRobots(this.model.robotsKeySet());

        locs.entrySet().stream().forEach((map -> {
            if(map.getValue() == 0) robots_rececao.add(map.getKey());
            else res.put(map.getValue(), map.getKey());
        }));

        this.showMapa.showThingsMapa(res);

        System.out.println("\nSelecione 1 para ver paletes em zona de receção (Pressione outra para sair)");
        try{
            if(Integer.parseInt(scin.nextLine()) ==1) this.showMapa.verZonaRececao(robots_rececao);
        }catch(NumberFormatException e){}
    }

    /**
     * Menu Utilizador - Opção 4
     * Estado - Ver mapa
     */
    private void verMapa(){ this.showMapa.showMapa();}

    private void menuAdmin() {
        Menu menu = new Menu(new String[]{
                "Simular requisição",
                "Testar programa"
        });

        // Registar pré-condições das transições´
        menu.setPreCondition(1, ()->this.model.haPaletes());

        // Registar os handlers
        menu.setHandler(1, ()->requisicao());
        menu.setHandler(2, ()->test());

        menu.run();
    }

    private void requisicao(){
        try {
            System.out.println("Paletes disponiveís para requisição: ");
            List<String> armazenadas = this.model.paletesArmazenadas();
            armazenadas.forEach(System.out::println);

            System.out.println("Indique quantas paletes pretende requisitar: ");
            int num = Integer.parseInt(scin.nextLine());
            System.out.println("Introduza os códigos das paletes:");

            List<String> requisitadas = new ArrayList<>();
            for(int i=0; i<num; i++ ) {
                String cod = scin.nextLine();
                if(this.model.existePalete(cod)) requisitadas.add(cod);
            }

            requisitadas.forEach(palete -> this.model.requisicao(palete));

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void test(){
        this.model.registaPaletes();
    }
}
