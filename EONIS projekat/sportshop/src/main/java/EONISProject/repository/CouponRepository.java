package EONISProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	Coupon findByCode(String code);
}
