package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.CategoryCreateDto;
import EONISProject.model.Category;
import EONISProject.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Transactional
    public Category create(CategoryCreateDto dto) {
        Category c = new Category(dto.id() ,dto.name(), dto.description());
        return categoryRepo.save(c);
    }
    
    @Transactional
    public Category update(Integer id, CategoryCreateDto dto) {
        Category existing = categoryRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Category not found: " + id));

        existing.setName(dto.name());
        existing.setDescription(dto.description());

        return categoryRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Category cat = categoryRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Category not found: " + id));

        categoryRepo.delete(cat);
    }

}
