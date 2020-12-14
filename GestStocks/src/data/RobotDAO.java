package data;

import model.Robot;
import model.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RobotDAO implements Map<String, Robot> {
    private static RobotDAO singleton = null;

    private RobotDAO() {
        try (Connection conn = DAOconnection.getConnection();
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Robot (" +
                    "idRobot varchar(10) NOT NULL PRIMARY KEY," +
                    "estado int(1) DEFAULT 0," +
                    "dintancia double(45) DEFAULT 0, " +
                    "localizacao int (10), foreign key(Localizacao) references turmas(idNodo))";
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
    public static RobotDAO getInstance() {
        if (RobotDAO.singleton == null) {
            RobotDAO.singleton = new RobotDAO();
            //UtilizadorDAO.singleton.createUserTables();
        }
        return RobotDAO.singleton;
    }

    @Override
    public int size() {
        return DAOconnection.size("Robot");
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
                     stm.executeQuery("SELECT idRobot FROM Robot WHERE idRobot='"+key.toString()+"'")) {
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
        Robot r = (Robot) value;
        return this.containsKey((r.getIdRobot()));
    }

    @Override
    public Robot get(Object key) {
        try (Connection conn = DAOconnection.getConnection()){
            Robot r = null;
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Robot WHERE idRobot='"+key+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                r = new Robot(rs.getString("idRobot"),rs.getString("estado"),rs.getString("distancia"), rs.getString("localizacao"));
            }
            return r;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Robot put(String key, Robot value) {
        return null;
    }

    @Override
    public Robot remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Robot> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Robot> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        return null;
    }
}
