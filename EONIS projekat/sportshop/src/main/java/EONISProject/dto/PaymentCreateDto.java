package EONISProject.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentCreateDto(
		@NotNull int id,
        @NotBlank(message = "Payment method is required") String method,
        @NotBlank(message = "Status is required") String status,
        @NotNull(message = "Amount is required") @Positive(message = "Amount must be greater than 0") BigDecimal amount,
        @NotNull(message = "Payment date is required") LocalDate paymentDate,
        @NotNull(message = "Order ID is required") int orderId
) {}
