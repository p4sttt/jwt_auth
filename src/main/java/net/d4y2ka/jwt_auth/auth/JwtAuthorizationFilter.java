package net.d4y2ka.jwt_auth.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.d4y2ka.jwt_auth.utlis.JwtCore;
import net.d4y2ka.jwt_auth.utlis.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
  private final Logger LOGGER;
  private final JwtCore jwtCore;
  private final ObjectMapper objectMapper;

  @Autowired
  JwtAuthorizationFilter(JwtCore jwtCore) {
    this.jwtCore = jwtCore;
    this.LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    this.objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String uri = request.getRequestURI();

    LOGGER.info("--------New HTTP request--------");
    LOGGER.info(uri);

    if (uri.startsWith("/api/auth") || uri.startsWith("/api/user")) {
      filterChain.doFilter(request, response);
      return;
    }

    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      objectMapper.writeValue(
          response.getWriter(), ResponseFactory.create(request.getRequestURI(), "Invalid token")
      );
      return;
    }

    String token = authorizationHeader.substring(7);
    LOGGER.info("--------This is JWT token--------");
    LOGGER.info(token);

    if (!jwtCore.verifyToken(token)) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      objectMapper.writeValue(
          response.getWriter(), ResponseFactory.create(request.getRequestURI(), "Invalid token")
      );
      return;
    }

    filterChain.doFilter(request, response);
  }
}
