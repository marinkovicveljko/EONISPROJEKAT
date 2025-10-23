package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.OrderItemCreateDto;
import EONISProject.model.OrderItem;
import EONISProject.service.OrderItemService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItem> all() {
        return orderItemService.getAll();
    }

    @PostMapping
    public ResponseEntity<OrderItem> create(@RequestBody @Valid OrderItemCreateDto dto) {
        OrderItem saved = orderItemService.create(dto);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(@PathVariable Integer id,
                                            @RequestBody @Valid OrderItemCreateDto dto) {
        OrderItem updated = orderItemService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}