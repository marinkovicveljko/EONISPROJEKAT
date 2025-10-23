package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.OrderCreateDto;
import EONISProject.model.Order;
import EONISProject.model.User;
import EONISProject.repository.OrderRepository;
import EONISProject.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository orderRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @Transactional
    public Order create(OrderCreateDto dto) {
        User u = userRepo.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.userId()));

        Order o = new Order(
                LocalDate.now(),
                dto.status(),
                dto.totalPrice(),
                u
        );
        o.setShippingDate(dto.shippingDate());
        o.setDiscount(dto.discount());
        o.setNote(dto.note());

        return orderRepo.save(o);
    }
    @Transactional
    public Order update(Integer id, OrderCreateDto dto) {
        Order existing = orderRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Order not found: " + id));

        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("User not found: " + dto.userId()));

        existing.setUser(user);
        existing.setStatus(dto.status());
        existing.setTotalPrice(dto.totalPrice());
        existing.setShippingDate(dto.shippingDate());
        existing.setDiscount(dto.discount());
        existing.setNote(dto.note());

        return orderRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Order not found: " + id));

        orderRepo.delete(order);
    }
    
    public List<Order> searchByStatus(String status) {
        return orderRepo.findByStatus(status);
    }

    public List<Order> searchByUserId(Integer userId) {
        return orderRepo.findByUserId(userId);
    }

}