package ge.giorgi.springbootdemo.Car.Error;

public class EngineInUseException extends RuntimeException {
    public EngineInUseException(String message) {
        super(message);
    }
}
