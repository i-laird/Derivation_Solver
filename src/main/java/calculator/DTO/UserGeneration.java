/* (C)2022 */
package calculator.DTO;

import calculator.annotation.FieldsMatch;
import calculator.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@FieldsMatch(
    field = "password",
    fieldMatch = "confirmPassword",
    message = "Passwords do not match!")
public class UserGeneration {
  @NotBlank @Email private String email;
  @Password private String password;
  private String confirmPassword;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
