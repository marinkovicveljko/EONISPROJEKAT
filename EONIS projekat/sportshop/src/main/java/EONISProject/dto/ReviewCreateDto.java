package EONISProject.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ReviewCreateDto(
		@NotNull int id,
        @NotNull(message = "Product ID is required") int productId,
        @NotNull(message = "User ID is required") int userId,
        @Min(value= 1, message="Rating must be at least 1") @Max(value= 5, message="Rating must be at most 5") int rating,
        @NotBlank(message = "Comment is required") String comment,
        @NotNull(message = "Date is required") LocalDate date
) {}
