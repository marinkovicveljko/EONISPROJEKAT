package EONISProject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import EONISProject.dto.UserCreateDto;
import EONISProject.exception.NotFoundException;
import EONISProject.model.User;
import EONISProject.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Transactional
    public User create(UserCreateDto dto) {
        if (userRepo.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email already registered!");
        }
        User u = new User(dto.name(), dto.surname(), dto.email(), passwordEncoder.encode(dto.password()), dto.role());
        return userRepo.save(u);
    }

    @Transactional
    public User update(Integer id, UserCreateDto dto) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        existing.setName(dto.name());
        existing.setSurname(dto.surname());
        existing.setEmail(dto.email());
        existing.setRole(dto.role());

        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.password()));
        }

        return userRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        userRepo.delete(user);
    }
    
    @Transactional(readOnly = true)
    public Page<User> searchByName(String name, Pageable pageable) {
        return userRepo.findByNameContainingIgnoreCase(name, pageable);
    }

    @Transactional(readOnly = true)
    public Page<User> searchBySurname(String surname, Pageable pageable) {
        return userRepo.findBySurnameContainingIgnoreCase(surname, pageable);
    }

    @Transactional(readOnly = true)
    public User searchByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public User getCurrentUser(String email) {
        return userRepo.findByEmail(email);
    }
}
