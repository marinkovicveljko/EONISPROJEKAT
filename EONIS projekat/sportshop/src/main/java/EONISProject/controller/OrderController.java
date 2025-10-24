package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.OrderCreateDto;
import EONISProject.model.Order;
import EONISProject.service.OrderService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Vrati sve porudžbine
    @GetMapping
    public List<Order> all() {
        return orderService.getAll();
    }

    // Kreiraj novu porudžbinu (na osnovu userId, adrese i stavki)
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid OrderCreateDto dto) {
        Order saved = orderService.create(dto);
        return ResponseEntity.ok(saved);
    }

    // Izmeni postojeću porudžbinu
    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Integer id,
                                        @RequestBody @Valid OrderCreateDto dto) {
        Order updated = orderService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Obriši porudžbinu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Pretraga po statusu
    @GetMapping("/search/by-status")
    public ResponseEntity<List<Order>> searchByStatus(@RequestParam String status) {
        return ResponseEntity.ok(orderService.searchByStatus(status));
    }

    // Pretraga po korisniku
    @GetMapping("/search/by-user")
    public ResponseEntity<List<Order>> searchByUser(@RequestParam Integer userId) {
        return ResponseEntity.ok(orderService.searchByUserId(userId));
    }
}
