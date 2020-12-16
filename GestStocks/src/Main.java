import model.GestStocksFacade;

import ui.TextUI;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        //GestStocksFacade facade = new GestStocksFacade(false, false);
        //facade.createMapa();
        //facade.addThings();

        try {
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }


    }
}
