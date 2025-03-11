package ge.giorgi.springbootdemo.Car.Controllers;

import ge.giorgi.springbootdemo.Car.Services.EngineService;
import ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO;
import ge.giorgi.springbootdemo.Car.Models.Requests.EngineRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static ge.giorgi.springbootdemo.Car.Security.AuthorizationConstants.ADMIN;
import static ge.giorgi.springbootdemo.Car.Security.AuthorizationConstants.USER_OR_ADMIN;

@RestController
@RequestMapping("/engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;

    @GetMapping
    @PreAuthorize(USER_OR_ADMIN)
    public Page<EngineDTO> getEngines(@RequestParam int page, @RequestParam int pageSize, @RequestParam double capacity){
        return engineService.getEngines(page, pageSize, capacity);
    }

    @GetMapping("{id}")
    @PreAuthorize(USER_OR_ADMIN)
    public ResponseEntity<EngineDTO> getEngine(@PathVariable long id){
        EngineDTO engine=engineService.getEngine(id);
        return ResponseEntity.ok(engine);

    }

    @PutMapping("{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<EngineDTO> updateEngine(@PathVariable long id, @RequestBody @Valid EngineRequest engineRequest){
        EngineDTO  engineDTO=engineService.updateEngine(id, engineRequest);
        return ResponseEntity.status(HttpStatus.OK).body(engineDTO);

    }
    @PostMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> addEngine(@RequestBody @Valid EngineRequest engineRequest){
        engineService.createEngine(engineRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Engine added successfully");
    }

    @DeleteMapping("{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> deleteEngine(@PathVariable long id){
        engineService.deleteEngine(id);
        return ResponseEntity.noContent().build();
    }
}
