package EONISProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
		@NotBlank Integer id,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Surname is required") String surname,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String password,
        @NotBlank(message = "Role is required") String role

		) {}
