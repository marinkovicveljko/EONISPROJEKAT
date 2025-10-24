package EONISProject.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderCreateDto(
        @NotNull Integer userId,

        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank String country,
        String note,
        @NotNull List<OrderItemCreateDto> items
) {}
