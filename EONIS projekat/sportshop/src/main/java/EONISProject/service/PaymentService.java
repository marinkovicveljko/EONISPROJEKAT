package EONISProject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import EONISProject.dto.*;
import EONISProject.exception.NotFoundException;
import EONISProject.model.*;
import EONISProject.repository.*;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentService(PaymentRepository paymentRepo, OrderRepository orderRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public Page<Payment> getAll(Pageable pageable) {
        return paymentRepo.findAll(pageable);
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
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));

        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new NotFoundException("Order not found: " + dto.orderId()));

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
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));
        paymentRepo.delete(payment);
    }

    public String processPayment(PaymentRequest request) {
        Stripe.apiKey = stripeSecretKey;

        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(request.getAmount().multiply(new java.math.BigDecimal(100)).longValue())
                            .setCurrency(request.getCurrency() != null ? request.getCurrency().toLowerCase() : "rsd")
                            .setDescription("Order ID: " + request.getOrderId())
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            Order order = orderRepo.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found: " + request.getOrderId()));

            Payment payment = new Payment();
            payment.setAmount(request.getAmount());
            payment.setMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CARD");
            payment.setStatus("CREATED");
            payment.setPaymentDate(java.time.LocalDate.now());
            payment.setOrder(order);

            paymentRepo.save(payment);

            order.setStatus("CREATED");
            orderRepo.save(order);

            return intent.getClientSecret();

        } catch (Exception e) {
            throw new RuntimeException("Stripe greška: " + e.getMessage(), e);
        }
    }

    public String handleWebhook(StripeWebhookEvent event) {
        Payment payment = paymentRepo.findById(event.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Order order = payment.getOrder();

        if ("payment_success".equalsIgnoreCase(event.getEvent())) {
            payment.setStatus("SUCCESS");
            order.setStatus("PAID");
        } else if ("payment_failed".equalsIgnoreCase(event.getEvent())) {
            payment.setStatus("FAILED");
            order.setStatus("FAILED");
        }

        paymentRepo.save(payment);
        orderRepo.save(order);

        return "Webhook processed: Payment #" + payment.getId() + " updated to " + payment.getStatus();
    }
}
