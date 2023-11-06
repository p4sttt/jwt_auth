package net.d4y2ka.jwt_auth.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.d4y2ka.jwt_auth.services.AuthService;
import net.d4y2ka.jwt_auth.utlis.response.Response;
import net.d4y2ka.jwt_auth.utlis.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;
  private final Logger LOGGER;

  @Autowired
  AuthController(AuthService authService) {
    this.authService = authService;
    this.LOGGER = LoggerFactory.getLogger(AuthController.class);
  }

  @PostMapping("/signin")
  public ResponseEntity<Response> signin(
      @Valid @RequestBody SigninRequestBody requestBody,
      BindingResult bindingResult,
      HttpServletRequest request
  ) {
    LOGGER.info("--------New Sign in request--------");
    LOGGER.info(requestBody.toString());

    if (bindingResult.hasErrors()) {

      LOGGER.warn("--------Request body validation error--------");
      LOGGER.warn(bindingResult.getFieldErrors().toString());

      return new ResponseEntity<>(
          ResponseFactory.create(request.getRequestURI(), "Validation error"), HttpStatus.BAD_REQUEST
      );
    }

    Response response = authService.signin(requestBody, request);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<Response> signup(
      @Valid @RequestBody SignupRequestBody requestBody,
      BindingResult bindingResult,
      HttpServletRequest request
  ) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity<>(
          ResponseFactory.create(request.getRequestURI(), "Validation error"), HttpStatus.BAD_REQUEST
      );
    }

    Response response = authService.signup(requestBody, request);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
