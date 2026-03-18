package calculator.service;

import calculator.DTO.DerivativeResponse;
import java.util.Map;

public interface CalculatorService {

  DerivativeResponse evaluateDerivative(String expression, char withRespectTo, Map<Character, Integer> evalPoints);

  double evaluateExpression(String expression, Map<Character, Integer> evalPoints);
}
