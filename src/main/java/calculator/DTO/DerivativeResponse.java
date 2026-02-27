package calculator.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response DTO
 *
 * @author Ian Laird
 */
@Data
@AllArgsConstructor
public class DerivativeResponse {
  // the calculated antiderivative.
  private String antiderivative;

  // the antiderivative value when evaluated.
  private Double result;
}
