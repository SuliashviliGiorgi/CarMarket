package ge.giorgi.springbootdemo.Car.Controllers;

import ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO;
import ge.giorgi.springbootdemo.Car.Models.Requests.CarRequest;
import ge.giorgi.springbootdemo.Car.Services.CarService;
import ge.giorgi.springbootdemo.Car.Security.AuthorizationConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    // Add a car (Admin only)
    @PostMapping
    @PreAuthorize(AuthorizationConstants.ADMIN)
    public ResponseEntity<String> addCar(@RequestBody @Valid CarRequest carRequest) {
        carService.addCar(carRequest);
        return ResponseEntity.ok("Car added successfully!");
    }

    // Update a car (Admin only)
    @PutMapping("{id}")
    @PreAuthorize(AuthorizationConstants.ADMIN)
    public ResponseEntity<String> updateCar(@PathVariable long id, @RequestBody @Valid CarRequest carRequest) {
        carService.updateCar(id, carRequest);
        return ResponseEntity.ok("Car updated successfully!");
    }

    // Delete a car (Admin only)
    @DeleteMapping("{id}")
    @PreAuthorize(AuthorizationConstants.ADMIN)
    public ResponseEntity<String> deleteCar(@PathVariable long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car deleted successfully!");
    }

    // ეს დამრჩა ასატვირთი
    // Get a specific car by ID (User/Admin)
    @GetMapping("{id}")
    @PreAuthorize(AuthorizationConstants.USER_OR_ADMIN)
    public ResponseEntity<CarDTO> getCar(@PathVariable long id) {
        return ResponseEntity.ok(carService.getCar(id));
    }

    // ესეც
    // Get paginated list of cars (User/Admin)
    @GetMapping
    @PreAuthorize(AuthorizationConstants.USER_OR_ADMIN)
    public Page<CarDTO> getCars(@RequestParam int page, @RequestParam int pageSize) {
        return carService.getCars(page, pageSize);
    }

    // Buy a car (User/Admin)
    @PostMapping("/buy/{userId}/{carId}")
    @PreAuthorize(AuthorizationConstants.USER_OR_ADMIN)
    public ResponseEntity<String> buyCar(@PathVariable long userId, @PathVariable long carId) {
        carService.buyCar(userId, carId);
        return ResponseEntity.ok("Car purchased successfully!");
    }

    // postman - ში ავტვირთო არ დამავიწყდეს
    // Sell a car (User/Admin)
    @PostMapping("/sell/{userId}/{carId}")
    @PreAuthorize(AuthorizationConstants.ADMIN)
    public ResponseEntity<String> sellCar(@PathVariable long userId, @PathVariable long carId) {
        carService.sellCar(userId, carId);
        return ResponseEntity.ok("Car sold successfully!");
    }
}

