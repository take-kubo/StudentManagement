package raisetech.StudentManagement.exception;

import lombok.Getter;

@Getter
public class IllegalRequestException extends RuntimeException {

  private final String fieldName;

  public IllegalRequestException(String fieldName, String message) {
    super(message);
    this.fieldName = fieldName;
  }

}
