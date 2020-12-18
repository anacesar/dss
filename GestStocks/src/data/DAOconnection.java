package data;

import java.sql.*;

public class DAOconnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Xanolindo17";
    private static final String CREDENTIALS = "?user="+USERNAME+"&password="+PASSWORD;
    private static final String DATABASE = "jdbc:mysql://localhost:3306/GestStocks";
    private static final String DB_INITIAL_URL = "jdbc:mysql://localhost:3306";


    /* Devolve conexão à base de dados */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
    }


    /* Cria base de dados geststocks */
    public static void createDB(){
        try(Connection conn = DriverManager.getConnection(DB_INITIAL_URL, USERNAME, PASSWORD)){
            Statement stm = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS GestStocks";
            stm.executeUpdate(sql);
            stm.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /* Comando genérico para criar tabelas */
    public static void createTables(String sql){
        try(Connection conn = DriverManager.getConnection(DATABASE+CREDENTIALS)){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /* Comando para apagar base de dados geststocks */
    public static void deleteDB(){
        try(Connection conn = DriverManager.getConnection(DATABASE+CREDENTIALS)){
            Statement stm = conn.createStatement();
            String sql = "DROP DATABASE GestStocks";
            stm.executeUpdate(sql);
            stm.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /*Devolve tamanho de tabela passada como argumento*/
    public static int size(String table) {
        int i = 0;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM " + table)) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }
}