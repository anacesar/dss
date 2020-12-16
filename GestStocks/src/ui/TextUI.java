package ui;

import model.GestStocksFacade;
import model.IGestStocks;

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

        this.model = new GestStocksFacade(false, false);
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Sistema de Gestão de Stocks!");
        this.menuPrincipal();
        System.out.println("Até breve...");
    }

    /**
     * Estado - Menu Principal
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                
        });

    }

}
