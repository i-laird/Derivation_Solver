package calculator.DTO;

/**
 * Response DTO
 *
 * @author Ian Laird
 */
public class DerivativeResponse {

  // the calculated antiderivative
  public String antiderivative;

  // the antiderivative value when evaluated
  public Double result;

  public DerivativeResponse(String antiderivative, Double result) {
    this.antiderivative = antiderivative;
    this.result = result;
  }
}
