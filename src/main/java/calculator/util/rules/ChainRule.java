/* (C)2022 */
package calculator.util.rules;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

public final class ChainRule extends DerivationRule {

  ChainRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public Term getDerivative(char withRespectTo) {
    if (this.terms.size() != 2) {
      return null;
    }
    ProductRule toReturn =
        RuleFactory.makeProductRule(
            this.terms.get(0).getDerivative(withRespectTo),
            this.terms.get(1).getDerivative(withRespectTo));
    if (this.negative) {
      toReturn.flipSign();
    }
    return toReturn;
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    return this.terms.get(0).evaluate(dims);
  }

  @Override
  public String toString() {
    return ""; // ChainRule is an intermediate wrapper; its terms handle their own string
    // representation
  }
}
