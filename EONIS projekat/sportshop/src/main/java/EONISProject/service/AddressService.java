package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.AddressCreateDto;
import EONISProject.model.Address;
import EONISProject.model.Order;
import EONISProject.model.User;
import EONISProject.repository.AddressRepository;
import EONISProject.repository.OrderRepository;
import EONISProject.repository.UserRepository;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepo;
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    public AddressService(AddressRepository addressRepo, UserRepository userRepo, OrderRepository orderRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional(readOnly = true)
    public List<Address> getAll() {
        return addressRepo.findAll();
    }

    @Transactional
    public Address create(AddressCreateDto dto) {
        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.userId()));

        Order order = orderRepo.findById(dto.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + dto.orderId()));

        Address address = new Address(
                dto.street(),
                dto.city(),
                dto.postalCode(),
                dto.country(),
                user,
                order
        );

        return addressRepo.save(address);
    }
    
    @Transactional
    public Address update(Integer id, AddressCreateDto dto) {
        Address existing = addressRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Address not found: " + id));

        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("User not found: " + dto.userId()));

        existing.setStreet(dto.street());
        existing.setCity(dto.city());
        existing.setCountry(dto.country());
        existing.setPostalCode(dto.postalCode());
        existing.setUser(user);

        return addressRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Address not found: " + id));
        addressRepo.delete(address);
    }

}