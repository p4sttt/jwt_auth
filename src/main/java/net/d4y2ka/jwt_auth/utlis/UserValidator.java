package net.d4y2ka.jwt_auth.utlis;

import net.d4y2ka.jwt_auth.entities.User;
import net.d4y2ka.jwt_auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
  @Autowired
  private UserService userService;

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.equals(User.class);
  }

  @Override
  public void validate(
      @NonNull Object target,
      @NonNull Errors errors
  ) {
    User user = (User) target;
    Optional<User> optionalUser = userService.getByEmail(user.getEmail());
    if (optionalUser.isPresent()) {
      errors.rejectValue("email", "", "User with this email already exist");
    }
  }
}
