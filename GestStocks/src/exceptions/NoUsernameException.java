package exceptions;

public class NoUsernameException extends Exception{
    public NoUsernameException() {
        super("O username inserido não existe!");
    }

    public NoUsernameException(String message) {
        super(message);
    }
}
