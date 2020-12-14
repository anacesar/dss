package data;

public class PaleteDAO {
    /*
    private static PaleteDAO singleton = null;

    private PaleteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconnection.DATABASE, DAOconnection.USERNAME, DAOconnection.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Utilizador (" +
                    "idUser varchar(10) NOT NULL PRIMARY KEY," +
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

    public static UtilizadorDAO getInstance() {
        if (UtilizadorDAO.singleton == null) {
            UtilizadorDAO.singleton = new UtilizadorDAO();
            //UtilizadorDAO.singleton.createUserTables();
        }
        return UtilizadorDAO.singleton;
    }

     */

}
