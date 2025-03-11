package ge.giorgi.springbootdemo.Car.Persistence.Repositories;

import ge.giorgi.springbootdemo.Car.Persistence.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
