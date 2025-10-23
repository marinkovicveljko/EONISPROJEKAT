package EONISProject.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Column(name = "created_date", nullable = false)
	    private LocalDate createdDate;

	    @Column(nullable = false, length = 50)
	    private String status;

	    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
	    private BigDecimal totalPrice;

	    @Column(name = "shipping_date")
	    private LocalDate shippingDate;

	    @Column(name = "discount", precision = 5, scale = 2)
	    private BigDecimal discount;

	    @Column(name = "note", length = 500)
	    private String note;

	    // 🔹 Relationship with user
	    @ManyToOne(optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    @JsonBackReference
	    private User user;
	    
	 // Address (1-1)
	    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private Address address;

	    // Payment (1-1)
	    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private Payment payment;

	    // OrderItems (1-N)
	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private List<OrderItem> items = new ArrayList<>();
	    
	    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private Coupon coupon;

	    public Coupon getCoupon() {
			return coupon;
		}

		public void setCoupon(Coupon coupon) {
			this.coupon = coupon;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public Payment getPayment() {
			return payment;
		}

		public void setPayment(Payment payment) {
			this.payment = payment;
		}

		public List<OrderItem> getItems() {
			return items;
		}

		public void setItems(List<OrderItem> items) {
			this.items = items;
		}

		public Order() {}

	    public Order(LocalDate createdDate, String status, BigDecimal totalPrice, User user) {
	        this.createdDate = createdDate;
	        this.status = status;
	        this.totalPrice = totalPrice;
	        this.user = user;
	    }

	    // Getters and setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }

	    public LocalDate getCreatedDate() { return createdDate; }
	    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }

	    public BigDecimal getTotalPrice() { return totalPrice; }
	    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

	    public LocalDate getShippingDate() { return shippingDate; }
	    public void setShippingDate(LocalDate shippingDate) { this.shippingDate = shippingDate; }

	    public BigDecimal getDiscount() { return discount; }
	    public void setDiscount(BigDecimal discount) { this.discount = discount; }

	    public String getNote() { return note; }
	    public void setNote(String note) { this.note = note; }

	    public User getUser() { return user; }
	    public void setUser(User user) { this.user = user; }
}
