package net.d4y2ka.jwt_auth.utlis.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Response {
  private final String path;
  private final LocalDateTime timestamp;

  public Response(String path) {
    this.path = path;
    this.timestamp = LocalDateTime.now();
  }
}
