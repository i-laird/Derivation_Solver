package calculator.DTO;

import java.io.Serial;
import java.io.Serializable;

public record JwtRequest(String username, String password) implements Serializable {
  @Serial private static final long serialVersionUID = 677867887L;
}
