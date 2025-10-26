package EONISProject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByStatusContainingIgnoreCase(String status, Pageable pageable);

    Page<Order> findByUserId(Integer userId, Pageable pageable);
}
