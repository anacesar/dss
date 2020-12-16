package exceptions;

public class paleteException extends Exception{

    public paleteException() {
        super("O código da palete não é válido! ");
    }

    public paleteException(String message) {
        super(message);
    }
}
