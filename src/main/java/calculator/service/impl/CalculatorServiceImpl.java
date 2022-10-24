/* (C)2022 */
package calculator.service.impl;

import calculator.DTO.DerivativeResponse;
import calculator.service.CalculatorService;
import calculator.util.Parser;
import calculator.util.StringToStream;
import calculator.util.terms.Term;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {

  @Override
  public DerivativeResponse evaluateDerivative(String expression, List<Integer> evalPoints) {
    Parser parser = new Parser(StringToStream.convertStringToStream(expression));
    Term derivative = parser.getDeriv();
    return new DerivativeResponse(derivative.toString(), derivative.evaluate(evalPoints));
  }

  @Override
  public double evaluateExpression(String expression) {
    Parser parser = new Parser(StringToStream.convertStringToStream(expression));
    Term root = parser.getRoot();
    return root.evaluate(null);
  }
}
