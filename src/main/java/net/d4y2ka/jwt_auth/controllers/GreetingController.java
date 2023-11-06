package net.d4y2ka.jwt_auth.controllers;

import jakarta.servlet.http.HttpServletRequest;
import net.d4y2ka.jwt_auth.utlis.response.Response;
import net.d4y2ka.jwt_auth.utlis.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {
  @GetMapping("")
  public ResponseEntity<Response> greeting(@RequestParam(required = false) String name, HttpServletRequest req) {
    if (name == null) {
      return new ResponseEntity<>(
          ResponseFactory.create(req.getRequestURI(), "Hello world!"),
          HttpStatus.OK
      );
    }

    String greetingMessage = String.format("Hello %s!", name);
    return new ResponseEntity<>(
        ResponseFactory.create(req.getRequestURI(), greetingMessage),
        HttpStatus.OK
    );
  }
}
