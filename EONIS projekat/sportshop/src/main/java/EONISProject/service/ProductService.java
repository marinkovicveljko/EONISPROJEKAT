package EONISProject.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.ProductCreateDto;
import EONISProject.model.Category;
import EONISProject.model.Product;
import EONISProject.repository.CategoryRepository;
import EONISProject.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    
    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }
    
    public List<Product> searchByName(String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }
    
    public List<Product> searchByCategory(Integer categoryId) {
        return productRepo.findByCategoryId(categoryId);
    }


    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Transactional
    public Product create(ProductCreateDto dto) {
        Category category = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + dto.categoryId()));

        Product product = new Product(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.stock(),
                category
        );
        
        product.setImageUrl(dto.imageUrl());

        return productRepo.save(product);
    }
    @Transactional
    public Product update(Integer id, ProductCreateDto dto) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Product not found: " + id));

        Category cat = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Category not found: " + dto.categoryId()));

       
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setPrice(dto.price());
        existing.setStock(dto.stock());
        existing.setCategory(cat);
        
        existing.setImageUrl(dto.imageUrl());

        return productRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Product not found: " + id));

        productRepo.delete(p);
    }
    
    public Optional<Product> getById(Integer id) {
        return productRepo.findById(id);
    }
    
    
}
