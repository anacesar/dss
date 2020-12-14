package data;

import model.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UtilizadorDAO implements Map<String, Utilizador> {
    private static UtilizadorDAO singleton = null;

    private UtilizadorDAO() {
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Utilizador (" +
                    "idUser int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "username varchar(45) DEFAULT NULL," +
                    "email varchar(45) DEFAULT NULL," +
                    "password varchar(45) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
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
            //UtilizadorDAO.singleton.createUserTables();
        }
        return UtilizadorDAO.singleton;
    }

    /** Limpa tabela de utilizadores. */
    public void clear () {
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Utilizador");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /** Apaga tabela de utilizadores */
    public void clearUserTable(){
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DROP TABLE Utilizador");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /** Comando usado para criar as tabelas dos utilizadores na base de dados. */
    public void createUserTables(){
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS Utilizador ");
        sql.append("(idUser VARCHAR(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, ");
        sql.append("username VARCHAR(255) DEFAULT NULL, ");
        sql.append("email VARCHAR(255) DEFAULT NULL, ");
        sql.append("password VARCHAR(255) DEFAULT NULL)");
        //sql.append("cargo int(2) DEFAULT 0, ");

        DAOconnection.createTables(sql.toString());
    }

    /**
     * @return número de utilizadores guardados na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Utilizador")) {
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

    /**
     * Método que verifica se existem utilizadores
     * @return true se existirem 0 utilizadores, false quando contrário
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que cerifica se um id de utilizador existe na base de dados
     * @param key id do utilizador
     * @return true se o utilizador existe
     * @throws NullPointerException Em caso de erro - utilizador não se encontra na base de dados
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT idUser FROM Utilizador WHERE idUser='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se um utilizador existe na base de dados                                                            !!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * Esta implementação é provisória. Devia testar todo o objecto e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException
     */
    @Override
    public boolean containsValue(Object value) {
        Utilizador u = (Utilizador) value;
        return this.containsKey((u.getIdUser()));
    }

    /**
     * Obter um utilizador, dado o seu id
     *
     * @param key id do utilizador
     * @return o utilizador caso exista (null caso contrário)
     * @throws NullPointerException Em caso de o utilizador não existir na base de dados
     */
    @Override
    public Utilizador get(Object key) {
        try (Connection conn = DAOconnection.getConnection()){
            Utilizador u = null;
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Utilizador WHERE idUser='"+key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                u = new Utilizador(rs.getString("idUser"),rs.getString("username"),rs.getString("email"), rs.getString("password"));
            }
            return u;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /**
     * Insere um utilizador na base de dados
     *
     * Caso o
     *
     * @param key não é utilizado porque chave primária
     * @param u o utilizador
     * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Utilizador put(String key, Utilizador u) {
        Utilizador res = null;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {

            // Actualizar o aluno
            stm.executeUpdate(
                    "INSERT INTO Utilizador VALUES (NULL, '"+u.getUsername()+"', '"+u.getEmail()+"', '"+u.getPassword()+"')" +
                            "ON DUPLICATE KEY UPDATE username=VALUES(username), email=VALUES(email)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Utilizador remove(Object key) {
        Utilizador u = this.get(key);
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Utilizador WHERE idUser='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return u;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Utilizador> utilizadores) {
        for(Utilizador u : utilizadores.values())
            this.put(u.getIdUser(), u);
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
                col.add(new Utilizador(rs.getString("idUser"),rs.getString("username"),rs.getString("email"), rs.getString("password")));
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
