package calculator.DTO;

import java.io.Serial;
import java.io.Serializable;

public record JwtResponse(String jwttoken) implements Serializable {
  @Serial private static final long serialVersionUID = 65565656555L;
}
