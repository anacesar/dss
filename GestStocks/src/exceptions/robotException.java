package exceptions;

public class robotException extends Exception{
    public robotException(){
        super("O robot não é válido!");
    }

    public robotException(String message){
        super(message);
    }
}
