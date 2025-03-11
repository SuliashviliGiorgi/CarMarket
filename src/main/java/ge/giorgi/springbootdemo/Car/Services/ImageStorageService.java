package ge.giorgi.springbootdemo.Car.Services;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Car;
import ge.giorgi.springbootdemo.Car.Persistence.Repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageStorageService {
    private static final String IMAGE_DIRECTORY = "C:/Users/Victus_HP/Desktop/carsAndUsers/";
    private final CarRepository carRepository;


    public String saveImage(Long carId, MultipartFile file) throws IOException
    {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        String fileName = file.getOriginalFilename();

        String modifiedFileName = System.currentTimeMillis() + "_" + fileName;
        File imageFile = new File(IMAGE_DIRECTORY + modifiedFileName);
        file.transferTo(imageFile);

        car.setImagePath(modifiedFileName);
        carRepository.save(car);

        return imageFile.getAbsolutePath();
    }
}