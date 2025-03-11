package ge.giorgi.springbootdemo.Car.Services;

import ge.giorgi.springbootdemo.Car.Error.InvalidPaginationException;
import ge.giorgi.springbootdemo.Car.Error.NotFoundException;
import ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO;
import ge.giorgi.springbootdemo.Car.Models.Requests.CarRequest;
import ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Car;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.CarRepository;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.AppUser;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final EngineService engineService;  // RESTORED EngineService
    private final AppUserRepository userRepository;  // Needed for buying/selling

    // Buying a car (user must have enough balance)
    public void buyCar(long userId, long carId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found"));

        if (user.getBalanceInCents() < car.getPriceInCents()) {
            throw new RuntimeException("Insufficient balance to buy this car.");
        }

        user.addCar(car);
        user.setBalanceInCents(user.getBalanceInCents() - car.getPriceInCents());
        car.setUser(user);

        userRepository.save(user);
        carRepository.save(car);
    }

    // Selling a car (user gets 80% of the car's value)
    public void sellCar(long userId, long carId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car not found"));

        if (!car.getUser().equals(user)) {
            throw new RuntimeException("You do not own this car.");
        }

        long salePrice = (car.getPriceInCents() * 80) / 100;
        user.setBalanceInCents(user.getBalanceInCents() + salePrice);
        car.setUser(null); // Car is no longer owned

        userRepository.save(user);
        carRepository.save(car);
    }

    // RESTORED Existing functionality - Get paginated cars
    public Page<CarDTO> getCars(int page, int pageSize) {
        if (page < 0 || pageSize <= 0) {
            throw new InvalidPaginationException("Page index must be non-negative and page size must be positive.");
        }
        return carRepository.findCars(PageRequest.of(page, pageSize));
    }

    // RESTORED Existing functionality - Add a new car
    public void addCar(CarRequest carRequest) {
        Car car = new Car();
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setDriveable(carRequest.isDriveable());
        car.setEngine(engineService.findEngine(carRequest.getEngineId()));
        carRepository.save(car);
    }


    // RESTORED Existing functionality - Update an existing car
    public void updateCar(long id, CarRequest carRequest) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> buildNotFoundException(id));

        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setDriveable(carRequest.isDriveable());
        car.setEngine(engineService.findEngine(carRequest.getEngineId())); // Using EngineService

        carRepository.save(car);
    }

    // RESTORED Existing functionality - Delete a car
    public void deleteCar(long id) {
        if (!carRepository.existsById(id)) {
            throw buildNotFoundException(id);
        }
        carRepository.deleteById(id);
    }

    // RESTORED Existing functionality - Get a specific car
    public CarDTO getCar(long id) {
        Car car = findCar(id);
        return mapCar(car);
    }

    // Helper function to find a car
    public Car findCar(long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> buildNotFoundException(id));
    }

    // Helper function to map Car entity to CarDTO
    private CarDTO mapCar(Car car) {
        return new CarDTO(
                car.getId(),
                car.getModel(),
                car.getYear(),
                car.isDriveable(),
                new EngineDTO(
                        car.getEngine().getId(),
                        car.getEngine().getHorsepower(),
                        car.getEngine().getCapacity()
                )
        );
    }

    private NotFoundException buildNotFoundException(Long id) {
        return new NotFoundException("Car with id: " + id + " was not found");
    }
}
