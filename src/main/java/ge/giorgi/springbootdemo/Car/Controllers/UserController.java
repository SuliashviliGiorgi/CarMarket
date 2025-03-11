package ge.giorgi.springbootdemo.Car.Controllers;

import ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO;
import ge.giorgi.springbootdemo.Car.Security.AuthorizationConstants;
import ge.giorgi.springbootdemo.Car.Services.UserService;
import ge.giorgi.springbootdemo.Car.Models.Requests.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody @Valid UserRequest userRequest)
    {
        userService.createUser(userRequest);
    }

    @GetMapping("{userId}")
    @PreAuthorize(AuthorizationConstants.ADMIN)
    public ResponseEntity<List<CarDTO>> getUserCars(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.getUserCars(userId));
    }
}
