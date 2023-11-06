package net.d4y2ka.jwt_auth.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SigninRequestBody {
  @NotNull(message = "Email can't be null")
  @Size(min = 2, max = 1488, message = "Email must contain between 2 and 1488 symbols")
  @Email(message = "Invalid email")
  private String email;

  @NotNull(message = "Email can't be null")
  @Size(min = 2, max = 1488, message = "Email must contain between 2 and 1488 symbols")
  private String password;
}
