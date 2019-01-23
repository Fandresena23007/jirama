package exception;

public class DateException extends Exception{
    String message;

    public DateException(String message){
        super();        
        setMessage(message);
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

}