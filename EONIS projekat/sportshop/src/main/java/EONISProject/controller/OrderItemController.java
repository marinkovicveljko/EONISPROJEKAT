package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.OrderItemCreateDto;
import EONISProject.model.OrderItem;
import EONISProject.service.OrderItemService;

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
    public ResponseEntity<Page<OrderItem>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(orderItemService.getAll(pageable));
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<OrderItem> create(@PathVariable Integer orderId,
                                            @RequestBody @Valid OrderItemCreateDto dto) {
        OrderItem saved = orderItemService.create(orderId, dto);
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
