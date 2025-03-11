package ge.giorgi.springbootdemo.Car.Error;


public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }

}
