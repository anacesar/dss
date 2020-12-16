package data;

import exceptions.NoLocalizacaoException;
import exceptions.NoPaleteException;
import model.Localizacao;
import model.Palete;
import model.Robot;
import model.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    /** Limpa tabela de paletes. */
    public void clear () {
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Palete");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /** Apaga tabela de paletes */
    public static void clearPaleteTable(){
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DROP TABLE Palete");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
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
            Localizacao l = null;

            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Palete WHERE codPalete='"+key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next()){
                p = new Palete(rs.getString("codPalete"), rs.getInt("localizacao"));
            }
            /*
            if (rs.next()){ //palete com codPalete pretendido
                sql = "SELECT * FROM localizacao WHERE idNodo='"+rs.getInt("localizacao")+"'";
                try (ResultSet rsa = stm.executeQuery(sql)) {
                    if (rsa.next()) {  // Encontrou a localizacao da palete
                        l = new Localizacao(rs.getInt("x"),
                                rsa.getInt("y"),
                                rsa.getBoolean("ocupado"));
                    } else {  //localizacao nao existe
                      throw new NoLocalizacaoException("A localização da palete pretendida não é válida! ");
                    }
                }
            }else /*nao ha palete com esse codigo{ throw new NoPaleteException(); }*/

            return p;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Palete put(String key, Palete p) {
        Palete res = null;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {

            // Actualizar o aluno
            stm.executeUpdate(
                    "INSERT INTO Palete VALUES ('"+p.getCodPalete()+"', '"+p.getLocalizacao()+"')" +
                            "ON DUPLICATE KEY UPDATE localizacao=VALUES(localizacao)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Palete remove(Object key) {
        Palete p = this.get(key);
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM Palete WHERE codPalete='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Palete> m) {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Palete> values() {
        try (Connection conn = DAOconnection.getConnection()){
            Collection<Palete> paletes = new ArrayList<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Palete";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                paletes.add(new Palete(rs.getString("codPalete"), rs.getInt("localizacao")));
            }
            return paletes;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Set<Entry<String, Palete>> entrySet() {
        return null;
    }

}
