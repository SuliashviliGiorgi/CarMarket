package ge.giorgi.springbootdemo.Car.Persistence.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="car")
@SequenceGenerator(name = "car_seq_gen", sequenceName = "car_seq", allocationSize = 1)
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(generator = "car_seq_gen", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "is_driving")
    private boolean driveable;

    @Column(name = "price_in_cents")
    private long priceInCents; // Added field for car price

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "image_path")
    private String imagePath;

}