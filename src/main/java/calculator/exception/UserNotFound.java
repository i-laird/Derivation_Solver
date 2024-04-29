/* (C)2022 */
package calculator.exception;

public class UserNotFound extends RuntimeException {
  public UserNotFound() {
    super();
  }

  public UserNotFound(String m) {
    super(m);
  }
}
