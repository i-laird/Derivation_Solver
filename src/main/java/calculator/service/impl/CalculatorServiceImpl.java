/* (C)2022 */
package calculator.service.impl;

import calculator.DTO.DerivativeResponse;
import calculator.service.CalculatorService;
import calculator.util.StringToStream;
import calculator.util.ast.AbstractSyntaxTree;
import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

/**
 * TO USE: 1) create the Parser using an input stream 2) Call get root to get the term at the base
 * of the AST.
 *
 * <p>TO GET DERIVATIVE: call getDerivative on the term returned from getRoot()
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {
  @Override
  public DerivativeResponse evaluateDerivative(
      String expression, ImmutableList<Integer> evalPoints) {
    AbstractSyntaxTree abstractSyntaxTree =
        new AbstractSyntaxTree(StringToStream.convertStringToStream(expression));
    Term derivative = abstractSyntaxTree.getDeriv();
    return new DerivativeResponse(derivative.toString(), derivative.evaluate(evalPoints));
  }

  @Override
  public double evaluateExpression(String expression, ImmutableList<Integer> evalPoints) {
    AbstractSyntaxTree abstractSyntaxTree =
        new AbstractSyntaxTree(StringToStream.convertStringToStream(expression));
    Term root = abstractSyntaxTree.getRoot();
    return root.evaluate(evalPoints);
  }
}
