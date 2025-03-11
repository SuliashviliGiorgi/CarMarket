package ge.giorgi.springbootdemo.Car.Services;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import ge.giorgi.springbootdemo.Car.Error.InvalidLoginException;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.AppUser;
import ge.giorgi.springbootdemo.Car.Persistence.Entities.Role;
import ge.giorgi.springbootdemo.Car.Models.Requests.LoginRequest;
import ge.giorgi.springbootdemo.Car.Models.Responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request){
        AppUser user = userService.getUser(request.getUsername());
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return generateLoginResponse(user);
        }

        throw new InvalidLoginException("Invalid login");
    }

    private LoginResponse generateLoginResponse(AppUser user) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                    .issuer("sampleauto.ge")
                    .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .build();

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new MACSigner(secretKey.getBytes()));

            return new LoginResponse(signedJWT.serialize());
        } catch (Exception ex){
            throw new InvalidLoginException("Failed to generate token");
        }
    }
}

