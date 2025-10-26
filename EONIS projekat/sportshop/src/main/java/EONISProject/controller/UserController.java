package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.UserCreateDto;
import EONISProject.model.User;
import EONISProject.service.UserService;

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
    public ResponseEntity<Page<User>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserCreateDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id,
                                       @RequestBody @Valid UserCreateDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/by-name")
    public ResponseEntity<Page<User>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(userService.searchByName(name, pageable));
    }

    @GetMapping("/search/by-surname")
    public ResponseEntity<Page<User>> searchBySurname(
            @RequestParam String surname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(userService.searchBySurname(surname, pageable));
    }

    @GetMapping("/search/by-email")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.searchByEmail(email));
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getCurrentUser(email));
    }
}
