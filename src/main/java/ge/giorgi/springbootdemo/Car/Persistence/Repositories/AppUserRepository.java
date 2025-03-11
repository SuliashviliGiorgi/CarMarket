package ge.giorgi.springbootdemo.Car.Persistence.Repositories;

import ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.username = :username")
    Optional<AppUser> findByUsername(String username);

    @Query("SELECT NEW ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO(c.id, c.model, c.year, c.driveable, " +
            "NEW ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO(e.id, e.horsepower, e.capacity)) " +
            "FROM Car c JOIN c.engine e WHERE c.user.id = :userId")
    Optional<List<CarDTO>> findUserCars(@Param("userId") Long userId);
}
