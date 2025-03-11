package ge.giorgi.springbootdemo.Car.Services;


import ge.giorgi.springbootdemo.Car.Error.NotFoundException;
import ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.AppUser;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.AppUserRepository;
import ge.giorgi.springbootdemo.Car.Models.Requests.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void createUser(UserRequest userRequest) {
        AppUser user = new AppUser();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(userRequest.getRoleIds().stream().
                map(roleService::getRole).collect(Collectors.toSet()));

        appUserRepository.save(user);
    }

    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<CarDTO> getUserCars(Long userId) {
        return appUserRepository.findUserCars(userId)
                .orElseThrow(() -> new NotFoundException("User cars not found"));
    }
}
