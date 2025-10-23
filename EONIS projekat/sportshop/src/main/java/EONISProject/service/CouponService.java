package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.CouponCreateDto;
import EONISProject.model.Coupon;
import EONISProject.model.Order;
import EONISProject.repository.CouponRepository;
import EONISProject.repository.OrderRepository;

import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepo;
    private final OrderRepository orderRepo;

    public CouponService(CouponRepository couponRepo, OrderRepository orderRepo) {
        this.couponRepo = couponRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public List<Coupon> getAll() {
        return couponRepo.findAll();
    }

    @Transactional
    public Coupon create(CouponCreateDto dto) {
        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + dto.orderId()));

        Coupon coupon = new Coupon(
                dto.code(),
                dto.discountPercent(),
                dto.expiryDate(),
                order
        );

        return couponRepo.save(coupon);
    }
    @Transactional
    public Coupon update(Integer id, CouponCreateDto dto) {
        Coupon existing = couponRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Coupon not found: " + id));

        existing.setCode(dto.code());
        existing.setDiscountPercent(dto.discountPercent());
        existing.setExpiryDate(dto.expiryDate());

        return couponRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Coupon coupon = couponRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Coupon not found: " + id));
        couponRepo.delete(coupon);
    }
    
    public Coupon searchByCode(String code) {
        return couponRepo.findByCode(code);
    }
}
