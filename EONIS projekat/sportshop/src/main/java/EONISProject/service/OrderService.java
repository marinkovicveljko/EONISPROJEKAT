package EONISProject.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.OrderCreateDto;
import EONISProject.dto.OrderItemCreateDto;
import EONISProject.model.Address;
import EONISProject.model.Order;
import EONISProject.model.OrderItem;
import EONISProject.model.Product;
import EONISProject.model.User;
import EONISProject.repository.OrderRepository;
import EONISProject.repository.ProductRepository;
import EONISProject.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, UserRepository userRepo,ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepo.findAll();
    }


    @Transactional
    public Order create(OrderCreateDto dto) {
        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedDate(LocalDate.now());
        order.setStatus("NEW");
        order.setNote(dto.note());

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemCreateDto itemDto : dto.items()) {
            Product product = productRepo.findById(itemDto.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal pricePerUnit = product.getPrice();
            BigDecimal itemTotal = pricePerUnit.multiply(BigDecimal.valueOf(itemDto.quantity()));

            OrderItem orderItem = new OrderItem(
                    itemDto.quantity(),
                    pricePerUnit,
                    itemTotal,
                    product,
                    order
            );

            orderItems.add(orderItem);
            totalPrice = totalPrice.add(itemTotal);
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        // 👇 dodaj adresu
        Address address = new Address();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setPostalCode(dto.postalCode());
        address.setCountry(dto.country());
        address.setUser(user);
        address.setOrder(order);

        order.setAddress(address);

        return orderRepo.save(order);
    }


    @Transactional
    public Order update(Integer id, OrderCreateDto dto) {
        Order existing = orderRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Order not found: " + id));

        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("User not found: " + dto.userId()));

        existing.setUser(user);
        existing.setStatus("PENDING");
        existing.setNote(dto.note());

        // ažuriraj adresu
        Address address = existing.getAddress();
        if (address == null) {
            address = new Address();
            address.setOrder(existing);
        }
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setPostalCode(dto.postalCode());
        address.setCountry(dto.country());
        existing.setAddress(address);

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
