package EONISProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
