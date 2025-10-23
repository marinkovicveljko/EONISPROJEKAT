package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        return paymentRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Payment not found: " + id));

        paymentRepo.delete(payment);
    }
    
    public String processPayment(PaymentRequest request) {
    	if(request.getAmount() == null || request.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0)
    	{
        return "Payment failed, amount must be greater than 0";
    	}
    	Payment payment = new Payment();
    	payment.setAmount(request.getAmount());
    	
    	payment.setStatus("SUCCESS");
    	
    	paymentRepo.save(payment);
    	
        return "Payment successful! Amount: " + request.getAmount() +
                (request.getCurrency() != null ? (" " + request.getCurrency()) : "");
     }
    public String handleWebhook(StripeWebhookEvent event) {
        Payment payment = paymentRepo.findById(event.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("payment_success".equalsIgnoreCase(event.getEvent())) {
            payment.setStatus("SUCCESS");
        } else if ("payment_failed".equalsIgnoreCase(event.getEvent())) {
            payment.setStatus("FAILED");
        } else {
            return "Unknown event type: " + event.getEvent();
        }

        paymentRepo.save(payment);
        return "Webhook processed: Payment #" + payment.getId() + " updated to " + payment.getStatus();
    }
  } 



