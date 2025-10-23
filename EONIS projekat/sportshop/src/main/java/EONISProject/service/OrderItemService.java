package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.OrderItemCreateDto;
import EONISProject.model.Order;
import EONISProject.model.OrderItem;
import EONISProject.model.Product;
import EONISProject.repository.OrderItemRepository;
import EONISProject.repository.OrderRepository;
import EONISProject.repository.ProductRepository;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public OrderItemService(OrderItemRepository orderItemRepo, ProductRepository productRepo, OrderRepository orderRepo) {
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getAll() {
        return orderItemRepo.findAll();
    }

    @Transactional
    public OrderItem create(OrderItemCreateDto dto) {
        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + dto.orderId()));

        OrderItem item = new OrderItem(
                dto.quantity(),
                dto.pricePerUnit(),
                dto.total(),
                product,
                order
        );

        return orderItemRepo.save(item);
    }
    @Transactional
    public OrderItem update(Integer id, OrderItemCreateDto dto) {
        OrderItem existing = orderItemRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("OrderItem not found: " + id));

        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Product not found: " + dto.productId()));

        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Order not found: " + dto.orderId()));

        existing.setProduct(product);
        existing.setOrder(order);
        existing.setQuantity(dto.quantity());
        existing.setPricePerUnit(dto.pricePerUnit());
        existing.setTotal(dto.total());

        return orderItemRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        OrderItem item = orderItemRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("OrderItem not found: " + id));

        orderItemRepo.delete(item);
    }

}
