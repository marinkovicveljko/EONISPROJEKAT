package EONISProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import EONISProject.dto.ReviewCreateDto;
import EONISProject.model.*;
import EONISProject.repository.*;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public ReviewService(ReviewRepository reviewRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.reviewRepo = reviewRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public List<Review> getAll() {
        return reviewRepo.findAll();
    }

    @Transactional
    public Review create(ReviewCreateDto dto) {
        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.userId()));

        Review review = new Review(
                dto.rating(),
                dto.comment(),
                dto.date(),
                product,
                user
        );

        return reviewRepo.save(review);
    }
    @Transactional
    public Review update(Integer id, ReviewCreateDto dto) {
        Review existing = reviewRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Review not found: " + id));

        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Product not found: " + dto.productId()));

        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("User not found: " + dto.userId()));

        existing.setProduct(product);
        existing.setUser(user);
        existing.setRating(dto.rating());
        existing.setComment(dto.comment());

        return reviewRepo.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new EONISProject.exception.NotFoundException("Review not found: " + id));
        reviewRepo.delete(review);
    }

}
