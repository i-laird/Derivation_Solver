package calculator.DTO;

import calculator.annotation.FieldsMatch;
import calculator.annotation.Password;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

 @FieldsMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
@Data
public class UserGeneration {

        @NotBlank
        @Email
        private String email;

        @Password
        private String password;

        private String confirmPassword;
}
