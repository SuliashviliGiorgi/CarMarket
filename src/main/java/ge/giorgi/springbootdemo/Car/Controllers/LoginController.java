package ge.giorgi.springbootdemo.Car.Controllers;

import ge.giorgi.springbootdemo.Car.Models.Requests.LoginRequest;
import ge.giorgi.springbootdemo.Car.Models.Responses.LoginResponse;
import ge.giorgi.springbootdemo.Car.Services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    LoginResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }
}
