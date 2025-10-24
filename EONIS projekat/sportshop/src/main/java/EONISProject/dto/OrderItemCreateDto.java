package EONISProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemCreateDto(
        @NotNull Integer productId,
        @Positive int quantity
) {}