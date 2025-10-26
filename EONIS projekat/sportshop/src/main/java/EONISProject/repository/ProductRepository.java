package EONISProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
