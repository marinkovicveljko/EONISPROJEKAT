package EONISProject.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findByNameContainingIgnoreCase(String name);
	List<Product> findByCategoryId(Integer categoryId);

}