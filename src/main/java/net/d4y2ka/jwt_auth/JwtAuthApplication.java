package net.d4y2ka.jwt_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
}
)
public class JwtAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(JwtAuthApplication.class, args);
  }

}
