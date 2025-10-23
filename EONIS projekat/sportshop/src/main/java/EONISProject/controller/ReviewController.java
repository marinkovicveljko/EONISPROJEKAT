package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.ReviewCreateDto;
import EONISProject.model.Review;
import EONISProject.service.ReviewService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> all() {
        return reviewService.getAll();
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody @Valid ReviewCreateDto dto) {
        Review saved = reviewService.create(dto);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Integer id,
                                         @RequestBody @Valid ReviewCreateDto dto) {
        Review updated = reviewService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
