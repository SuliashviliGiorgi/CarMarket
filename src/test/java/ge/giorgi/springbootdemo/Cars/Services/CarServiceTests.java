package ge.giorgi.springbootdemo.Cars.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import ge.giorgi.springbootdemo.Car.Error.NotFoundException;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.AppUser;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Car;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.AppUserRepository;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.CarRepository;
import ge.giorgi.springbootdemo.Car.Services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceTests {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService; // Assuming your class is named CarService

    private AppUser user;
    private Car car;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setBalanceInCents(100_000); // $1000

        car = new Car();
        car.setId(2L);
        car.setPriceInCents(50_000); // $500
    }

    @Test
    void buyCar_SuccessfulPurchase() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findById(2L)).thenReturn(Optional.of(car));

        carService.buyCar(1L, 2L);

        assertEquals(50_000, user.getBalanceInCents()); // Remaining balance should be $500
        assertTrue(user.getCars().contains(car));
        assertEquals(user, car.getUser());

        verify(userRepository).save(user);
        verify(carRepository).save(car);
    }

    @Test
    void buyCar_ThrowsException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> carService.buyCar(1L, 2L));
        assertEquals("User not found", exception.getMessage());

        verify(userRepository, never()).save(any());
        verify(carRepository, never()).save(any());
    }

    @Test
    void buyCar_ThrowsException_WhenCarNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> carService.buyCar(1L, 2L));
        assertEquals("Car not found", exception.getMessage());

        verify(userRepository, never()).save(any());
        verify(carRepository, never()).save(any());
    }

    @Test
    void buyCar_ThrowsException_WhenInsufficientBalance() {
        user.setBalanceInCents(30_000); // $300, less than car price

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findById(2L)).thenReturn(Optional.of(car));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> carService.buyCar(1L, 2L));
        assertEquals("Insufficient balance to buy this car.", exception.getMessage());

        assertFalse(user.getCars().contains(car));
        assertNull(car.getUser());

        verify(userRepository, never()).save(any());
        verify(carRepository, never()).save(any());
    }
}
