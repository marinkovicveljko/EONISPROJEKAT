package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.ProductCreateDto;
import EONISProject.model.Product;
import EONISProject.service.ProductService;
import EONISProject.repository.ProductRepository;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	
    private final ProductService productService;

    // Jedini konstruktor – samo servis!
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> all() {
        return productService.getAll();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid ProductCreateDto dto) {
        Product saved = productService.create(dto);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id,
                                          @RequestBody @Valid ProductCreateDto dto) {
        Product updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping("/search/by-name")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }
    
    @GetMapping("/search/by-category")
    public ResponseEntity<List<Product>> searchByCategory(@RequestParam Integer categoryId) {
        return ResponseEntity.ok(productService.searchByCategory(categoryId));
    }


}
