package EONISProject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderCreateDto(
		@NotNull int id,
	    @NotNull(message = "User ID is required") int userId,
	    @NotBlank(message = "Status is required") String status,
	    @NotNull(message = "Price is required") @Positive(message = "Total price must be greater than 0") BigDecimal totalPrice,
	    LocalDate shippingDate,
	    @Min(value = 0, message = "Discount cannot be negative") BigDecimal discount,
	    String note
	    ) {

}
