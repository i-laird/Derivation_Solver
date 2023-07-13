package calculator.service;

import calculator.DTO.DerivativeResponse;
import java.util.List;

public interface CalculatorService {
  DerivativeResponse evaluateDerivative(String expression, List<Integer> evalPoints);

  double evaluateExpression(String expression);
}
