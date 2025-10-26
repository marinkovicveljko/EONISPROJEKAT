package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.CouponCreateDto;
import EONISProject.model.Coupon;
import EONISProject.service.CouponService;

@Validated
@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "http://localhost:4200")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<Page<Coupon>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(couponService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody @Valid CouponCreateDto dto) {
        Coupon saved = couponService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> update(@PathVariable Integer id,
                                         @RequestBody @Valid CouponCreateDto dto) {
        Coupon updated = couponService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-code")
    public ResponseEntity<Coupon> searchByCode(@RequestParam String code) {
        return ResponseEntity.ok(couponService.searchByCode(code));
    }
}
