package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.AddressCreateDto;
import EONISProject.model.Address;
import EONISProject.service.AddressService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> all() {
        return addressService.getAll();
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody @Valid AddressCreateDto dto) {
        Address saved = addressService.create(dto);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Integer id,
                                          @RequestBody @Valid AddressCreateDto dto) {
        Address updated = addressService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}