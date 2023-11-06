package net.d4y2ka.jwt_auth.utlis.response;

import lombok.Getter;

@Getter
public class ResponseWithDataAndMessage<T> extends Response implements AbleToData<T>, AbleToMessage {
  private final String message;
  private final T data;

  public ResponseWithDataAndMessage(String path, String message, T data) {
    super(path);
    this.message = message;
    this.data = data;
  }
}
