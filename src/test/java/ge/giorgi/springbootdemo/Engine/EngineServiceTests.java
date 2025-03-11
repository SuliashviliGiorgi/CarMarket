package ge.giorgi.springbootdemo.Engine;

import ge.giorgi.springbootdemo.Car.Services.EngineService;
import ge.giorgi.springbootdemo.Car.Models.DTOs.EngineDTO;
import ge.giorgi.springbootdemo.Car.Models.Requests.EngineRequest;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Engine;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.EngineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EngineServiceTest {

    @Mock
    private EngineRepository engineRepository;

    @InjectMocks // 5 usages
    private EngineService engineService;

    @Test
    void testGetEngines() {
        // Given
        PageRequest pageRequest = PageRequest.of( 0, 10);
        Page<EngineDTO> enginePage = new PageImpl<>(List.of(new EngineDTO( 1L,  150,  2.0)));
        when(engineRepository.findEngines( 2.0, pageRequest)).thenReturn(enginePage);

        // When
        Page<EngineDTO> result = engineService.getEngines( 0,  10,  2.0);

        // Then
        assertEquals( 1, result.getContent().size());
        assertEquals( 2.0,
                result.getContent().get(0).getCapacity());
        verify(engineRepository).findEngines( 2.0, pageRequest);
    }

    @Test
    void testCreateEngine() {

        EngineRequest request = new EngineRequest( 200, 2.0);
        when(engineRepository.save(any(Engine.class))).thenReturn(buildEngine());

        // When
        engineService.createEngine(request);

        // Then
        verify(engineRepository).save(any(Engine.class));
    }

    @Test
    void testUpdateEngine() {
        // Given
        EngineRequest request = new EngineRequest( 200, 2.0);
        when(engineRepository.findById(1L)).thenReturn(java.util.Optional.of(buildEngine()));
        when(engineRepository.save(any(Engine.class))).thenReturn(buildEngine());

        // When
        EngineDTO result = engineService.updateEngine(1L, request);

        // Then
        assertEquals( 2.0, result.getCapacity());
        assertEquals( 200, result.getHorsePower());
        verify(engineRepository).findById(1L);
        verify(engineRepository).save(any(Engine.class));
    }

    @Test
    void testDeleteEngine() {
        engineService.deleteEngine( 1L);

        verify(engineRepository).deleteById(1L);
    }

    @Test
    void testFindEngine() {
        // Given
        when(engineRepository.findById(1L)).thenReturn(java.util.Optional.of(buildEngine()));

        // When
        Engine result = engineService.findEngine( 1L);

        // Then
        assertNotNull(result);
        assertEquals( 1L, result.getId());
        verify(engineRepository).findById(1L);
    }

    private Engine buildEngine() { // 4 usages
        Engine engine = new Engine();
        engine.setId(1L);
        engine.setCapacity(2.0);
        engine.setHorsepower(150);
        return engine;
    }
}

