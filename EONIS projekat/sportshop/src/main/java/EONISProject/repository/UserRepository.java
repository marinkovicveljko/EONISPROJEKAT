package EONISProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<User> findBySurnameContainingIgnoreCase(String surname, Pageable pageable);

    User findByEmail(String email);
}
