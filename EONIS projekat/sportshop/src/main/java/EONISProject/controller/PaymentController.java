package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.PaymentCreateDto;
import EONISProject.dto.PaymentRequest;
import EONISProject.dto.StripeWebhookEvent;
import EONISProject.model.Payment;
import EONISProject.service.PaymentService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> all() {
        return paymentService.getAll();
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody @Valid PaymentCreateDto dto) {
        Payment saved = paymentService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> update(@PathVariable Integer id,
                                          @RequestBody @Valid PaymentCreateDto dto) {
        Payment updated = paymentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ checkout vraća Stripe clientSecret
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody @Valid PaymentRequest request) {
        String clientSecret = paymentService.processPayment(request);
        return ResponseEntity.ok(clientSecret);
    }

    // ✅ webhook updateuje status plaćanja
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestBody @Valid StripeWebhookEvent event) {
        String result = paymentService.handleWebhook(event);
        return ResponseEntity.ok(result);
    }
}
