package ge.giorgi.springbootdemo.Car.ExceptionHandler;

import ge.giorgi.springbootdemo.Car.Error.EngineInUseException;
import ge.giorgi.springbootdemo.Car.Error.InvalidLoginException;
import ge.giorgi.springbootdemo.Car.Error.InvalidPaginationException;
import ge.giorgi.springbootdemo.Car.Error.NotFoundException;
import ge.giorgi.springbootdemo.Car.Models.DTOs.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationError(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getFieldErrors().stream().map(
                        fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).
                collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("invalid-request", errorMessage));

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("not-found", exception.getMessage()));
    }

    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<String> handleInvalidPagination(InvalidPaginationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EngineInUseException.class)
    public ResponseEntity<String> handleEngineInUseException(EngineInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorDTO> handleInvalidLoginException(InvalidLoginException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO("invalid-login", ex.getMessage()));
    }

}
