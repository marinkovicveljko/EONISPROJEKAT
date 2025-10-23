package EONISProject.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="users")
public class User {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 25)
    private String name;
    
    @Column(nullable = false, length = 35)
    private String surname;

    @Column(nullable = false, length = 70)
    private String email;

    @Column(nullable = false, length = 150)
    private String password;
    
    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public User() {}

    public User(String name, String surname, String email, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() { 
    	return id; 
    	}
    public void setId(int id) { 
    	this.id = id;
    	}

    public String getName() { 
    	return name; 
    	}
    public void setName(String name) {
    	this.name = name;
    	}
    
    public String getSurname() { 
    	return surname; 
    	}
    public void setSurname(String surname) {
    	this.surname = surname;
    	}

    public String getEmail() {
    	return email; 
    	}
    public void setEmail(String email) { 
    	this.email = email;
    	}

    public String getPassword() {
    	return password; 
    	}
    public void setPassword(String password) { 
    	this.password = password;
    	}

    public List<Order> getOrders() { 
    	return orders;
    	}
    public void setOrders(List<Order> orders) { 
    	this.orders = orders; 
    	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
