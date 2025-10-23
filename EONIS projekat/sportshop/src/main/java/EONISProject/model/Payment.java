package EONISProject.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String method;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    // 🔹 Order reference
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonManagedReference
    private Order order;

    public Payment() {}

    public Payment(String method, String status, BigDecimal amount, LocalDate paymentDate, Order order) {
        this.method = method;
        this.status = status;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.order = order;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}