package EONISProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
