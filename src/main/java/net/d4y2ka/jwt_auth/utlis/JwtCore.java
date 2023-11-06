package net.d4y2ka.jwt_auth.utlis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import net.d4y2ka.jwt_auth.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Component
public class JwtCore {
  private final Logger LOGGER = LoggerFactory.getLogger(JwtCore.class);
  private final Algorithm algorithm;

  @Autowired
  JwtCore(Environment environment) {
    String secretKey = environment.getProperty("jwt.secretKey");

    assert secretKey != null;
    LOGGER.info("--------This is secretKey--------");
    LOGGER.info(secretKey);

    this.algorithm = Algorithm.HMAC256(secretKey);
  }

  public String generateToken(User user) {
    Instant now = Instant.now();
    Instant expireTime = now.plus(30, ChronoUnit.DAYS);

    return JWT.create()
        .withSubject(user.getId().toString())
        .withClaim("email", user.getEmail())
        .withClaim("iat", now)
        .withClaim("exp", expireTime)
        .sign(algorithm);
  }

  public boolean verifyToken(String token) {
    JWTVerifier verifier = JWT.require(algorithm).build();
    try {
      verifier.verify(token);
    } catch (JWTVerificationException e) {

      LOGGER.warn("--------JWTVerificationException was caught--------");
      LOGGER.warn(e.toString());

      return false;
    }
    return true;
  }
}
