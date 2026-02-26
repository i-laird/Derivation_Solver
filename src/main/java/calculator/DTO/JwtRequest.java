package calculator.DTO;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
  @Serial private static final long serialVersionUID = 677867887L;
  private String username;
  private String password;
}
