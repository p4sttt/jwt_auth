package net.d4y2ka.jwt_auth.utlis.response;

import lombok.Getter;

@Getter
public class ResponseWithMessage extends Response implements AbleToMessage{
  private final String message;

  public ResponseWithMessage(String path, String message) {
    super(path);
    this.message = message;
  }
}
