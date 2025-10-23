package EONISProject.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressCreateDto(
		@NotNull int id,
        @NotBlank(message = "Street is required") String street,
        @NotBlank(message = "City is required") String city,
        @NotBlank(message = "Postal code is required") String postalCode,
        @NotBlank(message = "Country is required") String country,
        @NotNull(message = "User ID is required") int userId,
        @NotNull(message = "Order ID is required") int orderId
) {}

