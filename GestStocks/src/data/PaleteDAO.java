package data;


import model.Localizacao;
import model.Palete;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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


    /** Limpa tabela de paletes */
    public static void clearPaleteTable(){
        try (Connection conn = DAOconnection.getConnection()) {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Palete");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public int size() {
        return DAOconnection.size("Palete");
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
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

    @Override
    public boolean containsValue(Object value) {
        Palete p = (Palete) value;
        return this.containsKey((p.getCodPalete()));
    }

    @Override
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

            return p;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Palete put(String key, Palete p) {
        Palete res = null;
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {

            // Inserir/Actualizar a palete
            stm.executeUpdate(
                    "INSERT INTO Palete VALUES ('"+p.getCodPalete()+"', '"+p.getLocalizacao()+"')" +
                            "ON DUPLICATE KEY UPDATE localizacao=VALUES(localizacao)");
            //Atualizar localizacao da palete como ocupada se != 0
            if(p.getLocalizacao()!=0)
                stm.executeUpdate(
                        "UPDATE Localizacao SET ocupado=1 where idNodo="+p.getLocalizacao());

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Palete remove(Object key){
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
    public Set<String> keySet(){
        try (Connection conn = DAOconnection.getConnection()){
            Set<String> keySet = new HashSet<>();
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM palete";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                keySet.add(rs.getString("codPalete"));
            }
            return keySet;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Collection<Palete> values(){
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
    public Set<Entry<String, Palete>> entrySet(){ return null; }

    @Override
    public void putAll(Map<? extends String, ? extends Palete> m){}

    @Override
    public void clear(){}

}
