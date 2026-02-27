/* (C)2022 */
package calculator.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException() {
    super();
  }

  public UserAlreadyExistsException(String m) {
    super(m);
  }
}
