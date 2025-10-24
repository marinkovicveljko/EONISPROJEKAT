package EONISProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);
	 List<User> findByNameContainingIgnoreCase(String name);
	 List<User> findBySurnameContainingIgnoreCase(String surname);
	 User findByEmail(String email);

}
