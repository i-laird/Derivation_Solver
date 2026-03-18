/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

public final class NaturalLogRule extends DerivationRule {
  public static final int ARGUMENT_INDEX = 0;

  NaturalLogRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * d/dx(lnx) = 1/x
   *
   * @return
   */
  @Override
  public Term getDerivative(char withRespectTo) {

    Term argument = this.terms.get(ARGUMENT_INDEX);
    return makeProductRule(makeFracRule(argument, new Term(1)), argument.getDerivative(withRespectTo));
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    return Math.log(this.terms.get(ARGUMENT_INDEX).evaluate(dims));
  }

  @Override
  public String toString() {
    return "ln (" + this.terms.get(ARGUMENT_INDEX) + " )";
  }
}
