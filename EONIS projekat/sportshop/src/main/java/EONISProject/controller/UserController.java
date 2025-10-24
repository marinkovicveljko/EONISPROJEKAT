package EONISProject.controller;


import jakarta.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.UserCreateDto;
import EONISProject.model.User;
import EONISProject.service.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> all() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserCreateDto dto) {
        User saved = userService.create(dto);
        return ResponseEntity.ok(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id,
                                       @RequestBody @Valid UserCreateDto dto) {
        User updated = userService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/by-name")
    public ResponseEntity<List<User>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchByName(name));
    }

    @GetMapping("/search/by-surname")
    public ResponseEntity<List<User>> searchBySurname(@RequestParam String surname) {
        return ResponseEntity.ok(userService.searchBySurname(surname));
    }

    @GetMapping("/search/by-email")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.searchByEmail(email));
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String email = authentication.getName(); // email iz tokena
        User user = userService.getCurrentUser(email);
        return ResponseEntity.ok(user);
    }

}
