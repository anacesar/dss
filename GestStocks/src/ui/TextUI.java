package ui;

import model.GestStocksFacade;
import model.IGestStocks;
import model.Utilizador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUI {
    private IGestStocks model;
    private Scanner scin;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {

        this.model = new GestStocksFacade(false,false);
        scin = new Scanner(System.in);
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
                System.out.println("Gestor adicionado");
            }else
                System.out.println("Esse usarname já existe!");

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void login(){
        boolean sucesso=true;
        try {
            System.out.println("Username: ");
            String username = scin.nextLine();
            if(this.model.existeUtilizador(username)) {
                System.out.println("Password: ");
                String pass = scin.nextLine();
                if(!this.model.validaUser(username,pass)) System.out.println("Username e Password não coincide!");
                else menuUtilizador();
            }else {
                sucesso=false;
                System.out.println("Não se encontra registado no sistema! \nPor favor faça o registo ou tente novamente.");
            }

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private void menuUtilizador() {
        Menu menu = new Menu(new String[]{
                "Consultar localizações"
        });

        // Registar pré-condições das transições´
        menu.setPreCondition(1, ()->this.model.haPaletes());

        // Registar os handlers
        menu.setHandler(1, ()->consultaLocalizacoes());

        menu.run();
    }

    private void consultaLocalizacoes(){
        try {
            List<String> locValidas = new ArrayList<>();
            List<String> locInvalidas = new ArrayList<>();

            System.out.println("Quantas localizações pretende consultar: ");
            Integer num = Integer.valueOf(scin.nextLine());
            System.out.println("Introduza os códigos das paletes:");

            for(int i=0; i<num; i++ ) {
                String cod = scin.nextLine();
                if(!this.model.existePalete(cod)) locInvalidas.add(cod);
                else locValidas.add(cod);
            }
            if (!locInvalidas.isEmpty()) System.out.println("\nOs códigos seguintes são inválidos: " + locInvalidas);
            if (!locValidas.isEmpty()) this.model.localizacoes(locValidas).forEach((k, v)-> System.out.println("\nPalete: " + k + "\nLocalização : " +v));
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
    }


}
