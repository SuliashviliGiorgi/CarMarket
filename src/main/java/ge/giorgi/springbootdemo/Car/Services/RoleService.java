package ge.giorgi.springbootdemo.Car.Services;

import ge.giorgi.springbootdemo.Car.Error.NotFoundException;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Role;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(Long id){
        return roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Role with id: "+ id + " does not exists"));
    }
}
