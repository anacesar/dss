package exceptions;

public class usernameException extends Exception{
    public usernameException() {
        super("O username inserido não existe!");
    }

    public usernameException(String message) {
        super(message);
    }
}
