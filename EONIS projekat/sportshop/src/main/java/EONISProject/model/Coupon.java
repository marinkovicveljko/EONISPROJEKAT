package EONISProject.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "discount_percent", nullable = false)
    private int discountPercent;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    // 🔹 Reference to Order (1–1)
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @JsonManagedReference
    private Order order;

    public Coupon() {}

    public Coupon(String code, int discountPercent, LocalDate expiryDate, Order order) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.expiryDate = expiryDate;
        this.order = order;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
