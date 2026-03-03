package calculator.DTO;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A request for a mathematical expression. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerivativeRequest implements Serializable {
  @Serial private static final long serialVersionUID = 6778676666L;
  @NotBlank private String expression;
  private List<Integer> points;
}
