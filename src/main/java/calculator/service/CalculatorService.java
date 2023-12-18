package calculator.service;

import calculator.DTO.DerivativeResponse;
import com.google.common.collect.ImmutableList;

public interface CalculatorService {

  DerivativeResponse evaluateDerivative(String expression, ImmutableList<Integer> evalPoints);

  double evaluateExpression(String expression, ImmutableList<Integer> evalPoints);
}
