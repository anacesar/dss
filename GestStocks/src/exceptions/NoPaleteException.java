package exceptions;

public class NoPaleteException extends Exception{

    public NoPaleteException() {
        super("O código da palete não é válido! ");
    }

    public NoPaleteException(String message) {
        super(message);
    }
}
