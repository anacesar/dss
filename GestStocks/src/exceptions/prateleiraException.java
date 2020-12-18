package exceptions;

public class prateleiraException extends Exception {

    public prateleiraException(){ super("Não há prateleiras disponíveis para armazenamento! "); }

    public prateleiraException(String message){
        super(message);
    }
}
