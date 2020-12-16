package data;

import exceptions.NoLocalizacaoException;
import model.Localizacao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LocalizacaoDAO implements Map<Integer, Localizacao> {
    private static LocalizacaoDAO singleton = null;

    private LocalizacaoDAO() {
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Localizacao (" +
                    "idNodo int(10) NOT NULL PRIMARY KEY," +
                    "x int(10) DEFAULT 0," +
                    "y int(10) DEFAULT 0," +
                    "ocupado BOOLEAN DEFAULT FALSE)";
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
    public static LocalizacaoDAO getInstance() {
        if (LocalizacaoDAO.singleton == null) {
            LocalizacaoDAO.singleton = new LocalizacaoDAO();
        }
        return LocalizacaoDAO.singleton;
    }

    /** Limpa tabela de localizações. */
    public void clear () {
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Localizacao");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /** Apaga tabela de localizações */
    public static void clearUserTable(){
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DROP TABLE Localizacao");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public int size() {
        return DAOconnection.size("Localizacao");
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean containsKey(Object key){
        boolean r;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM Localizacao WHERE idNodo='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }


    /**
     *
     * @param key idNodo do mapa de localizações
     * @return Localizacao associada ao idNodo(key) - null caso contrário
     */
    @Override
    public Localizacao get(Object key) {
        try (Connection conn = DAOconnection.getConnection()){
            Localizacao l = null;
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Localizacao WHERE idNodo='"+key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){ //idNodo válido
                l = new Localizacao(rs.getInt("x"),rs.getInt("y"), rs.getBoolean("ocupado"));
            }else /*localizacao invalida */{
                throw new NoLocalizacaoException();
            }
            return l;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Localizacao put(Integer key, Localizacao loc) {
        Localizacao res = null;
        int ocupado = loc.getOcupado() ? 1 : 0;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {

            // Actualizar o aluno
            stm.executeUpdate(
                    "INSERT INTO Localizacao VALUES ('"+key+ "', '"+loc.getX()+"', '"+loc.getY()+"', '"+ocupado+"')" +
                            "ON DUPLICATE KEY UPDATE x=VALUES(x), y=VALUES(y), ocupado=VALUES(ocupado)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Localizacao remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Localizacao> m) {

    }


    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<Localizacao> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, Localizacao>> entrySet() {
        return null;
    }
}
