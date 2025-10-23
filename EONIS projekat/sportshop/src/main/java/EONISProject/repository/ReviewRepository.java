package EONISProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
