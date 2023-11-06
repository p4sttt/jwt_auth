package net.d4y2ka.jwt_auth.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.d4y2ka.jwt_auth.entities.User;
import net.d4y2ka.jwt_auth.services.UserService;
import net.d4y2ka.jwt_auth.utlis.UserValidator;
import net.d4y2ka.jwt_auth.utlis.response.Response;
import net.d4y2ka.jwt_auth.utlis.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;
  private final UserValidator userValidator;

  @Autowired
  UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  @GetMapping("")
  public ResponseEntity<Response> getAllUsers(HttpServletRequest req) {
    List<User> usersList = userService.getAll();
    return new ResponseEntity<>(
        ResponseFactory.create(req.getRequestURI(), usersList), HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response> getSingleUser(@PathVariable UUID id, HttpServletRequest req) {
    Optional<User> optionalUser = userService.getById(id);
    return optionalUser.map(user -> new ResponseEntity<>(
        ResponseFactory.create(req.getRequestURI(), user), HttpStatus.OK
    )).orElseGet(() -> new ResponseEntity<>(
        ResponseFactory.create(req.getRequestURI(), "User not found"), HttpStatus.BAD_REQUEST
    ));
  }

  @PostMapping("")
  public ResponseEntity<Response> createNewUser(
      @Valid @RequestBody User user,
      BindingResult bindingResult,
      HttpServletRequest req
  ) {
    userValidator.validate(user, bindingResult);
    if (bindingResult.hasErrors()) {
      Response response = ResponseFactory.create(req.getRequestURI(), "Validation error", bindingResult.getSuppressedFields());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    User createdUser = userService.save(user);
    Response response = ResponseFactory.create(req.getRequestURI(), "User success created", createdUser);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
