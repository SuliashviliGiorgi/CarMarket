package ge.giorgi.springbootdemo.Car.Models.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EngineDTO {
    private long id;
    private int horsePower;
    private double capacity;
}
