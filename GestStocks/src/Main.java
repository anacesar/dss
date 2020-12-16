import data.DAOconnection;
import model.GestStocksFacade;
import model.Palete;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        GestStocksFacade facade = new GestStocksFacade(false, false);
        //facade.createMapa();
        facade.addThings();



    }
}
