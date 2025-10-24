package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.OrderItemCreateDto;
import EONISProject.exception.NotFoundException;
import EONISProject.model.Order;
import EONISProject.model.OrderItem;
import EONISProject.model.Product;
import EONISProject.repository.OrderItemRepository;
import EONISProject.repository.OrderRepository;
import EONISProject.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public OrderItemService(OrderItemRepository orderItemRepo,
                            ProductRepository productRepo,
                            OrderRepository orderRepo) {
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getAll() {
        return orderItemRepo.findAll();
    }

    @Transactional
    public OrderItem create(Integer orderId, OrderItemCreateDto dto) {
        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        BigDecimal pricePerUnit = product.getPrice();
        BigDecimal total = pricePerUnit.multiply(BigDecimal.valueOf(dto.quantity()));

        OrderItem item = new OrderItem(
                dto.quantity(),
                pricePerUnit,
                total,
                product,
                order
        );

        // smanji stock proizvoda
        product.setStock(product.getStock() - dto.quantity());
        productRepo.save(product);

        // ažuriraj total order-a
        order.setTotalPrice(order.getTotalPrice().add(total));
        orderRepo.save(order);

        return orderItemRepo.save(item);
    }

    @Transactional
    public OrderItem update(Integer id, OrderItemCreateDto dto) {
        OrderItem existing = orderItemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("OrderItem not found: " + id));

        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product not found: " + dto.productId()));

        Order order = existing.getOrder();

        BigDecimal pricePerUnit = product.getPrice();
        BigDecimal total = pricePerUnit.multiply(BigDecimal.valueOf(dto.quantity()));

        existing.setProduct(product);
        existing.setQuantity(dto.quantity());
        existing.setPricePerUnit(pricePerUnit);
        existing.setTotal(total);

        // update order total price
        order.setTotalPrice(order.getItems().stream()
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderRepo.save(order);

        return orderItemRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        OrderItem item = orderItemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("OrderItem not found: " + id));

        Order order = item.getOrder();

        // ukloni stavku i ažuriraj total order-a
        orderItemRepo.delete(item);
        order.setTotalPrice(order.getItems().stream()
                .filter(i -> i.getId() != id)
                .map(OrderItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderRepo.save(order);
    }
}
