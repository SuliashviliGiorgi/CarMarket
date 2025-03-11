package ge.giorgi.springbootdemo.Car.Models.Requests;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarRequest {
    @NotBlank
    @Size(max=20)
    private String model;
    @Min(1940)
    private int year;
    private boolean driveable;
    @Positive
    private Long EngineId;



}
