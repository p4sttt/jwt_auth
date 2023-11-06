package net.d4y2ka.jwt_auth.services;

import jakarta.servlet.http.HttpServletRequest;
import net.d4y2ka.jwt_auth.controllers.SigninRequestBody;
import net.d4y2ka.jwt_auth.controllers.SignupRequestBody;
import net.d4y2ka.jwt_auth.entities.User;
import net.d4y2ka.jwt_auth.utlis.JwtCore;
import net.d4y2ka.jwt_auth.utlis.response.Response;
import net.d4y2ka.jwt_auth.utlis.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
  private final UserService userService;
  private final JwtCore jwtCore;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  AuthService(UserService userService, JwtCore jwtCore, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtCore = jwtCore;
    this.passwordEncoder = passwordEncoder;
  }

  public Response signin(SigninRequestBody requestBody, HttpServletRequest request) {
    Optional<User> optionalUser = userService.getByEmail(requestBody.getEmail());

    if (optionalUser.isEmpty()) {
      return ResponseFactory.create(request.getRequestURI(), "Invalid EMAIL or password");
    }

    User user = optionalUser.get();

    if (!passwordEncoder.matches(requestBody.getPassword(), user.getPassword())) {
      return ResponseFactory.create(request.getRequestURI(), "Invalid email or PASSWORD");
    }

    String jwtToken = jwtCore.generateToken(user);
    Map<String, Object> data = new HashMap<>();
    data.put("token", jwtToken);

    return ResponseFactory.create(request.getRequestURI(), "Success", data);
  }

  public Response signup(SignupRequestBody requestBody, HttpServletRequest request) {
    Optional<User> optionalUser = userService.getByEmail(requestBody.getEmail());

    if (optionalUser.isPresent()) {
      return ResponseFactory.create(request.getRequestURI(), "User already exist");
    }

    User createdUser = userService.save(new User(requestBody.getEmail(), requestBody.getPassword()));

    String jwtToken = jwtCore.generateToken(createdUser);
    Map<String, Object> data = new HashMap<>();
    data.put("token", jwtToken);

    return ResponseFactory.create(request.getRequestURI(), "Success", data);
  }
}
