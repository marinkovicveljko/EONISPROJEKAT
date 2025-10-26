package EONISProject.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import EONISProject.dto.ReviewCreateDto;
import EONISProject.model.Review;
import EONISProject.service.ReviewService;

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
    public ResponseEntity<Page<Review>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(reviewService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody @Valid ReviewCreateDto dto) {
        return ResponseEntity.ok(reviewService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Integer id,
                                         @RequestBody @Valid ReviewCreateDto dto) {
        return ResponseEntity.ok(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
