package exceptions;

public class NoLocalizacaoException extends Exception {

    public NoLocalizacaoException() {
        super("A localização não é válida! ");
    }

    public NoLocalizacaoException(String message) {
        super(message);
    }
}
