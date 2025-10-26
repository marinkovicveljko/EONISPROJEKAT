package EONISProject.controller;

import EONISProject.dto.*;
import EONISProject.exception.NotFoundException;
import EONISProject.model.*;
import EONISProject.repository.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public AdminController(UserRepository userRepository,
                           ProductRepository productRepository,
                           CategoryRepository categoryRepository,
                           OrderRepository orderRepository,
                           PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    // ------------------- USERS -------------------
    @GetMapping("/users")
    public ResponseEntity<Page<UserCreateDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(userRepository.findAll(pageable).map(
                u -> new UserCreateDto(
                        u.getId(), u.getName(), u.getSurname(),
                        u.getEmail(), u.getPassword(), u.getRole()
                )
        ));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    // ------------------- PRODUCTS -------------------
    @GetMapping("/products")
    public ResponseEntity<Page<ProductCreateDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(productRepository.findAll(pageable).map(
                p -> new ProductCreateDto(
                        p.getId(), p.getName(), p.getDescription(),
                        p.getPrice(), p.getStock(),
                        p.getCategory().getId(), p.getImageUrl()
                )
        ));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductCreateDto> getProduct(@PathVariable Integer id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return ResponseEntity.ok(new ProductCreateDto(
                p.getId(), p.getName(), p.getDescription(),
                p.getPrice(), p.getStock(),
                p.getCategory().getId(), p.getImageUrl()
        ));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductCreateDto> createProduct(@RequestBody @Valid ProductCreateDto dto) {
        Category cat = categoryRepository.findById(1)
                .orElseThrow(() -> new NotFoundException("Default category not found"));
        Product p = new Product(dto.name(), dto.description(), dto.price(), dto.stock(), cat);
        p.setImageUrl(dto.imageUrl());
        Product saved = productRepository.save(p);
        return ResponseEntity.ok(new ProductCreateDto(
                saved.getId(), saved.getName(), saved.getDescription(),
                saved.getPrice(), saved.getStock(),
                1, saved.getImageUrl()
        ));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductCreateDto> updateProduct(@PathVariable Integer id,
                                                          @RequestBody @Valid ProductCreateDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        Category cat = categoryRepository.findById(1)
                .orElseThrow(() -> new NotFoundException("Default category not found"));

        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setPrice(dto.price() != null ? dto.price() : BigDecimal.ZERO);
        existing.setStock(dto.stock());
        existing.setImageUrl(dto.imageUrl());
        existing.setCategory(cat);

        Product updated = productRepository.save(existing);

        return ResponseEntity.ok(new ProductCreateDto(
                updated.getId(), updated.getName(), updated.getDescription(),
                updated.getPrice(), updated.getStock(),
                1, updated.getImageUrl()
        ));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

    // ------------------- ORDERS -------------------
    @GetMapping("/orders")
    public ResponseEntity<Page<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(orderRepository.findAll(pageable));
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer id,
                                                   @RequestParam String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(status);
        return ResponseEntity.ok(orderRepository.save(order));
    }

    // ------------------- PAYMENTS -------------------
    @GetMapping("/payments")
    public ResponseEntity<Page<Payment>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(paymentRepository.findAll(pageable));
    }
}
