package net.d4y2ka.jwt_auth.utlis.response;

import lombok.Getter;

@Getter
public class ResponseWithData<T> extends Response implements AbleToData<T>{
  private final T data;
  public ResponseWithData(String path, T data) {
    super(path);
    this.data = data;
  }
}
