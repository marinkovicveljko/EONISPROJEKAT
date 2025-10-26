package EONISProject.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateDto(
		@NotNull int id,
        @NotBlank(message ="Product name is required") @Size(max = 150) String name,
        @NotBlank(message ="Description is required")@Size(max = 1000) String description,
        @NotNull(message = "Price is required") @Positive(message = "Price must be greater than 0") BigDecimal price,
        @NotNull(message = "Stock is required") @Min(value = 0, message="Stock cannot be negative") int stock,
        @NotNull(message = "Category ID is required") int categoryId,
        String imageUrl
) {}
