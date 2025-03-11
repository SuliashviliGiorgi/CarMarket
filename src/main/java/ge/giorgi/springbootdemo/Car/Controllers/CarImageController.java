package ge.giorgi.springbootdemo.Car.Controllers;

import ge.giorgi.springbootdemo.Car.Security.AuthorizationConstants;
import ge.giorgi.springbootdemo.Car.Services.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/car-images")
@RequiredArgsConstructor
public class CarImageController
{
    private  final ImageStorageService imageStorageService;

    @PostMapping("/upload/{carId}")
    @PreAuthorize(AuthorizationConstants.ADMIN)
    public ResponseEntity<String> uploadCarImage(@PathVariable Long carId, @RequestParam("file") MultipartFile file)
    {
        try
        {
            String response = imageStorageService.saveImage(carId,file);
            return ResponseEntity.ok("Image uploaded successfully: " + response);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok("Image uploaded unsuccessfully: ");
        }
    }
}
