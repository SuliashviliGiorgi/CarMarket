package ge.giorgi.springbootdemo.Car.Persistence.Repositories;

import ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT NEW ge.giorgi.springbootdemo.Car.Models.DTOs.CarDTO(c.id, c.model, c.year, c.driveable, " +
            "NEW ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO(e.id, e.horsepower, e.capacity))" +
            "FROM Car c JOIN c.engine e"
    )
    Page<CarDTO> findCars(Pageable pageable);
}
