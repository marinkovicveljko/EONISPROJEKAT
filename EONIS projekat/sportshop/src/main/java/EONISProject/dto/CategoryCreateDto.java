package EONISProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCreateDto(
		@NotNull int id,
        @NotBlank(message = "Category name is required") String name,
        @NotBlank(message = "Description is required") String description
) {}
