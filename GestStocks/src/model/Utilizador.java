package model;

public class Utilizador {
    private String username;
    private String email;
    private String password;
    //private int cargo; //0--> gestor, 1-->servidor de produção

    public Utilizador(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
