package exceptions;

public class localizacaoException extends Exception {

    public localizacaoException() {
        super("A localização não é válida! ");
    }

    public localizacaoException(String message) {
        super(message);
    }
}
