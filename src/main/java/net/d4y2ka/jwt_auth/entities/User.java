package net.d4y2ka.jwt_auth.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotNull
  @Size(min = 2, max = 1488)
  @Email
  private String email;

  @NotNull
  @NotBlank
  @Size(min = 5, max = 1488)
  private String password;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
