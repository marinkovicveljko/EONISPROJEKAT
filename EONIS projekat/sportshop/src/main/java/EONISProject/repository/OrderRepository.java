package EONISProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(String status);
    List<Order> findByUserId(Integer userId);
}
