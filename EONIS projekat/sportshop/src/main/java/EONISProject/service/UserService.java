package EONISProject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.UserCreateDto;
import EONISProject.model.User;
import EONISProject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Transactional
    public User create(UserCreateDto dto) {
        if (userRepo.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email already registered!");
        }
        User u = new User(dto.name(), dto.surname(), dto.email(), dto.password(), dto.role());
        return userRepo.save(u);
    }
    @Transactional
    public User update(Integer id, UserCreateDto dto) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("User not found: " + id));

        existing.setName(dto.name());
        existing.setSurname(dto.surname());
        existing.setEmail(dto.email());
        existing.setRole(dto.role());

        // ako DTO sadrži password i nije prazan, re-encode
        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.password()));
        }

        return userRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("User not found: " + id));

        userRepo.delete(user);
    }
    
    public List<User> searchByName(String name) {
        return userRepo.findByNameContainingIgnoreCase(name);
    }

    public List<User> searchBySurname(String surname) {
        return userRepo.findBySurnameContainingIgnoreCase(surname);
    }

    public User searchByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public User getCurrentUser(String email) {
        return userRepo.findByEmail(email);
    }

}
