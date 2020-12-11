package data;

import model.Utilizador;

import java.sql.*;
import java.util.*;

public class UtilizadorDAO implements Map<String, Utilizador> {
    private static UtilizadorDAO singleton = null;

    private UtilizadorDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static UtilizadorDAO getInstance() {
        if (UtilizadorDAO.singleton == null) {
            UtilizadorDAO.singleton = new UtilizadorDAO();
        }
        return UtilizadorDAO.singleton;
    }

    /*
        try (Connection conn =
                     DriverManager.getConnection("jdbc:mysql://"+ DATABASE+CREDENTIALS);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS salas (" +
                    "Num varchar(10) NOT NULL PRIMARY KEY," +
                    "Edificio varchar(45) DEFAULT NULL," +
                    "Capacidade int(4) DEFAULT 0)";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS turmas (" +
                    "Id varchar(10) NOT NULL PRIMARY KEY," +
                    "Sala varchar(10) DEFAULT NULL," +
                    "foreign key(Sala) references salas(Num))";
            stm.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS alunos (" +
                    "Num varchar(10) NOT NULL PRIMARY KEY," +
                    "Nome varchar(45) DEFAULT NULL," +
                    "Email varchar(45) DEFAULT NULL," +
                    "Turma varchar(10), foreign key(Turma) references turmas(Id))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }*/

    /* Limpa tabela de utilizadores. */
    public void clear () {
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Utilizador");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }


    /* Apaga tabela de utilizadores */
    public void clearUserTable(){
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DROP TABLE Utilizador");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /* Comando usado para criar as tabelas dos utilizadores na base de dados. */
    public void createUserTables(){
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS Utilizador ");
        sql.append("(idUser int(4) NOT NULL PRIMARY KEY, ");
        sql.append("username VARCHAR(255) DEFAULT NULL, ");
        sql.append("email VARCHAR(255) DEFAULT NULL, ");
        sql.append("password VARCHAR(255) DEFAULT NULL)");
        //sql.append("cargo int(2) DEFAULT 0, ");

        DAOconnection.createTables(sql.toString());
    }

    public void delete(Utilizador u){
        try (Connection conn = DAOconnection.getConnection()){
            String query = "DELETE FROM USERS WHERE idUser = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, u.getIdUser());
            preparedStmt.execute();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /* Introduz um utilizador na tabela apropriada */
    public void put(Utilizador u){
        try (Connection conn = DAOconnection.getConnection()) {
            String query = " INSERT INTO USERS (idUser, username, email, password)"
                    + " VALUES (?, ?, ?, ?)";
            // create the mysql insert preparedstatement
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt (1, u.getIdUser());
            stm.setString (2, u.getUsername());
            stm.setString (3, u.getEmail());
            stm.setString (4, u.getPassword());
            //stm.setInt (5, u.getCargo());
            stm.executeUpdate();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /* Devolve um utilizador com base no seu id (userKey) */
    public Utilizador get(Integer userKey){
        try (Connection conn = DAOconnection.getConnection()){
            Utilizador u = null;
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Utilizador WHERE idUser='"+userKey+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                u = new Utilizador(rs.getInt("idUser"),rs.getString("username"),rs.getString("email"), rs.getString("password"));
            }
            return u;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /* Devolveuma lista com todos os utilizadores presentes na base de dados */
    public Collection<Utilizador> getAllUsers(){
        try (Connection conn = DAOconnection.getConnection()){
            Collection<Utilizador> col = new ArrayList<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Utilizador";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                col.add(new Utilizador(rs.getInt("idUser"),rs.getString("username"),rs.getString("email"), rs.getString("password")));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }



    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Utilizador get(Object key) {
        return null;
    }

    @Override
    public Utilizador put(String key, Utilizador value) {
        return null;
    }

    @Override
    public Utilizador remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Utilizador> m) {

    }

    @Override
    /* Devolve o keySet dos utilizadores presentes na tabela */
    public Set<String> keySet(){
        try (Connection conn = DAOconnection.getConnection()){
            Set<String> keySet = new HashSet<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Utilizador";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                keySet.add(String.valueOf(rs.getInt("idUser")));
            }
            return keySet;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Collection<Utilizador> values() {
        try (Connection conn = DAOconnection.getConnection()){
            Collection<Utilizador> col = new ArrayList<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM USERS";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                col.add(new Utilizador(rs.getInt("idUser"),rs.getString("username"),rs.getString("email"), rs.getString("password")));
            }
            return col;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Set<Entry<String, Utilizador>> entrySet() {
        return null;
    }

}
