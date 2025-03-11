package ge.giorgi.springbootdemo.Car.Persistence.Repositories;

import ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Engine;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

public interface EngineRepository extends JpaRepository<Engine, Long> {

    @Query("SELECT NEW ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO(e.id, e.horsepower, e.capacity)" +
            "FROM Engine e WHERE e.capacity = :capacity ")
    Page<EngineDTO> findEngines(double capacity, Pageable pageable);

    @Query("SELECT COUNT(c) > 0 FROM Car c WHERE c.engine.id = :engineId")
    boolean existsCarByEngineId(Long engineId);
}
