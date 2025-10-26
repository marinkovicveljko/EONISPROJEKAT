package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import EONISProject.dto.ProductCreateDto;
import EONISProject.exception.NotFoundException;
import EONISProject.model.*;
import EONISProject.repository.*;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    
    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }
    
    @Transactional(readOnly = true)
    public Page<Product> searchByName(String name, Pageable pageable) {
        return productRepo.findByNameContainingIgnoreCase(name, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Product> searchByCategory(Integer categoryId, Pageable pageable) {
        return productRepo.findByCategoryId(categoryId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> getAll(Pageable pageable) {
        return productRepo.findAll(pageable);
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
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        Category cat = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + dto.categoryId()));

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
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        productRepo.delete(p);
    }
    
    public Optional<Product> getById(Integer id) {
        return productRepo.findById(id);
    }
}
