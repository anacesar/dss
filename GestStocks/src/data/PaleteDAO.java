package data;

import model.Palete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class PaleteDAO implements Map<String, Palete> {

    private static PaleteDAO singleton = null;

    private PaleteDAO() {
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Palete (" +
                    "codPalete varchar(10) NOT NULL PRIMARY KEY," +
                    "localizacao int(10), foreign key(Localizacao) references Localizacao(idNodo))";
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
    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
            //UtilizadorDAO.singleton.createUserTables();
        }
        return PaleteDAO.singleton;
    }


    public int size() {
        return DAOconnection.size("Palete");
    }


    public boolean isEmpty() {
        return this.size() == 0;
    }


    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT codPalete FROM Palete WHERE codPalete='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    public boolean containsValue(Object value) {
        Palete p = (Palete) value;
        return this.containsKey((p.getCodPalete()));
    }

    public Palete get(Object key) {
        try (Connection conn = DAOconnection.getConnection()){
            Palete p = null;
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Palete WHERE codPalete='"+key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                p = new Palete(rs.getString("codPalete"));
            }
            return p;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Palete put(String key, Palete value) {
        return null;
    }

    @Override
    public Palete remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Palete> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Palete> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Palete>> entrySet() {
        return null;
    }

}
