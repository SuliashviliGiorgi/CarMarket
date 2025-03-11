package ge.giorgi.springbootdemo.Car.Models.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private String errorCode;
    private String errorMessage;
}
