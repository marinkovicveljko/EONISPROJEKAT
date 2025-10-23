package EONISProject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}