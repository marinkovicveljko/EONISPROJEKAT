package EONISProject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import EONISProject.dto.PaymentCreateDto;
import EONISProject.dto.PaymentRequest;
import EONISProject.dto.StripeWebhookEvent;
import EONISProject.model.Payment;
import EONISProject.model.Order;
import EONISProject.repository.PaymentRepository;
import EONISProject.repository.OrderRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;

    // ✅ Stripe secret key se vuče iz application.properties
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentService(PaymentRepository paymentRepo, OrderRepository orderRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    @Transactional
    public Payment create(PaymentCreateDto dto) {
        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + dto.orderId()));

        Payment payment = new Payment(
                dto.method(),
                dto.status(),
                dto.amount(),
                dto.paymentDate(),
                order
        );

        return paymentRepo.save(payment);
    }

    @Transactional
    public Payment update(Integer id, PaymentCreateDto dto) {
        Payment existing = paymentRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Payment not found: " + id));

        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Order not found: " + dto.orderId()));

        existing.setOrder(order);
        existing.setAmount(dto.amount());
        existing.setPaymentDate(dto.paymentDate());
        existing.setMethod(dto.method());
        existing.setStatus(dto.status());

        return paymentRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Payment not found: " + id));

        paymentRepo.delete(payment);
    }

    // ✅ Stripe checkout
    public String processPayment(PaymentRequest request) {
        Stripe.apiKey = stripeSecretKey;

        try {
            // 1. Kreiraj PaymentIntent na Stripe
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(request.getAmount().multiply(new java.math.BigDecimal(100)).longValue()) // u centima
                            .setCurrency(request.getCurrency() != null ? request.getCurrency().toLowerCase() : "rsd")
                            .setDescription("Order ID: " + request.getOrderId())
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // 2. Poveži sa našom bazom
            Order order = orderRepo.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found: " + request.getOrderId()));

            Payment payment = new Payment();
            payment.setAmount(request.getAmount());
            payment.setMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CARD");
            payment.setStatus("CREATED");
            payment.setPaymentDate(java.time.LocalDate.now());
            payment.setOrder(order);

            paymentRepo.save(payment);

            // 3. Vrati clientSecret frontendu (Angular ga koristi sa publishable key-om)
            return intent.getClientSecret();

        } catch (Exception e) {
            throw new RuntimeException("Stripe greška: " + e.getMessage(), e);
        }
    }

    // ✅ Stripe webhook za potvrdu plaćanja
    public String handleWebhook(StripeWebhookEvent event) {
        Payment payment = paymentRepo.findById(event.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("payment_success".equalsIgnoreCase(event.getEvent())) {
            payment.setStatus("SUCCESS");
        } else if ("payment_failed".equalsIgnoreCase(event.getEvent())) {
            payment.setStatus("FAILED");
        }

        paymentRepo.save(payment);

        return "Webhook processed: Payment #" + payment.getId() + " updated to " + payment.getStatus();
    }
}
