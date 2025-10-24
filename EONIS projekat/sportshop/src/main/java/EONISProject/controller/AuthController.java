package EONISProject.controller;

import EONISProject.dto.UserCreateDto;
import EONISProject.model.User;
import EONISProject.repository.UserRepository;
import EONISProject.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email already taken");
            return ResponseEntity.badRequest().body(error);
        }

        User user = new User(dto.name(), dto.surname(), dto.email(),
                passwordEncoder.encode(dto.password()), dto.role());
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "email", user.getEmail(),
                "role", user.getRole()
        ));

        return ResponseEntity.ok(response);
    }

}
