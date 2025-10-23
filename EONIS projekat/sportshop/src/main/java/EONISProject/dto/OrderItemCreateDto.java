package EONISProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemCreateDto(
		@NotNull int id,
        @NotNull(message = "Product ID is required") int productId,
        @NotNull(message = "Order ID is required") int orderId,
        @NotNull(message = "Quantity is required") @Positive(message = "Quantity must be greater than 0") int quantity,
        @NotNull(message = "Price per unit is required") @Positive(message = "Price per unit must be greater than 0") BigDecimal pricePerUnit,
        @NotNull(message = "Total is required") @Positive(message = "Total must be greater than 0") BigDecimal total
) {}