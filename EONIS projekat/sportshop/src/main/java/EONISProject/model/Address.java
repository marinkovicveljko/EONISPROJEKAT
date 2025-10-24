package EONISProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(nullable = false, length = 50)
    private String country;

    // 🔹 User reference
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    // 🔹 Order reference
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    public Address() {}

    public Address(String street, String city, String postalCode, String country, User user, Order order) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.user = user;
        this.order = order;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}