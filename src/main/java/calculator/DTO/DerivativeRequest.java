package calculator.DTO;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/** A request for a mathematical expression. */
public record DerivativeRequest(
    @NotBlank String expression,
    @NotBlank String withRespectTo,
    Map<String, Integer> points)
    implements Serializable {
  @Serial private static final long serialVersionUID = 6778676666L;
}
