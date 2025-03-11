package ge.giorgi.springbootdemo.Car.Error;

public class InvalidLoginException extends RuntimeException{

    public InvalidLoginException(String exception){
        super(exception);
    }
}
