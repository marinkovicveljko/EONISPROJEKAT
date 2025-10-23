package EONISProject.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CouponCreateDto(
		@NotNull int id,
        @NotBlank(message = "Coupon code is required") String code,
        @Min(value= 1, message = "Discount must be at least 1%") @Max(value=100, message = "Discount cannot be more than 100%") int discountPercent,
        @NotNull(message = "Expiration date is required") LocalDate expiryDate,
        @NotNull(message = "Order ID is required") int orderId
) {}
