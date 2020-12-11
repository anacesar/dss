package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOconnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Anacesar99";
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "localhost:3306/GestStocks";

    /* Devolve conexão à base de dados */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://"+ DATABASE+CREDENTIALS);
    }

    /* Cria base de dados mediacenter */
    public static void createDB(){
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+ DATABASE+CREDENTIALS)){
            Statement stm = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS GestStocks";
            stm.executeUpdate(sql);
            stm.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /* Comando genérico para criar tabelas */
    public static void createTables(String sql){
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+ DATABASE+CREDENTIALS)){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /* Comando para apagar base de dados mediacenter */
    public static void deleteDB(){
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+ DATABASE+CREDENTIALS)){
            Statement stm = conn.createStatement();
            String sql = "DROP DATABASE GestStocks";
            stm.executeUpdate(sql);
            stm.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}