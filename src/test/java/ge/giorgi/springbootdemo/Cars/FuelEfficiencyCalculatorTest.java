package ge.giorgi.springbootdemo.Cars;

import ge.giorgi.springbootdemo.Car.Extensions.FuelEfficiencyCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FuelEfficiencyCalculatorTest {

    private final FuelEfficiencyCalculator fuelEfficiencyCalculator=new FuelEfficiencyCalculator();

    @Test
    void shouldCalculateFuelEfficiencyCorrectly(){
        double horsePower = 400;
        double capacity = 4.4;
        double weightInKg = 1800;

        double expectedValue = 0.8;
        double actualResult = fuelEfficiencyCalculator.calculateFuelEfficiency(horsePower, capacity, weightInKg);

        Assertions.assertEquals(expectedValue, actualResult);
    }

    @Test
    void shouldThrowExceptionIfInvalidHorsePower() {
        double horsePower = -1;
        double capacity = 4.4;
        double weightKg = 1800;

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> fuelEfficiencyCalculator.calculateFuelEfficiency(horsePower, capacity, weightKg));
    }

    @Test
    void shouldThrowExceptionIfInvalidCapacity() {
        double horsePower = 400;
        double capacity = -1;
        double weightKg = 1800;

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> fuelEfficiencyCalculator.calculateFuelEfficiency(horsePower, capacity, weightKg));
    }

    @Test
    void shouldThrowExceptionIfInvalidWeight() {
        double horsePower = 400;
        double capacity = 4.4;
        double weightKg = -1;

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> fuelEfficiencyCalculator.calculateFuelEfficiency(horsePower, capacity, weightKg));
    }
}
