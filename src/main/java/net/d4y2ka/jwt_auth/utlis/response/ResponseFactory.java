package net.d4y2ka.jwt_auth.utlis.response;

public class ResponseFactory {
  public static Response create(String path, String message) {
    return new ResponseWithMessage(path, message);
  }

  public static <T> Response create(String path, T data) {
    return new ResponseWithData<>(path, data);
  }

  public static <T> Response create(String path, String message, T data) {
    return new ResponseWithDataAndMessage<>(path, message, data);
  }
}
