/* (C)2022 */
package calculator.util.rules;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class ChainRule extends DerivationRule {

  @Override
  public Term getDerivative() {
    if (this.terms.size() != 2) {
      return null;
    }
    ProductRule toReturn =
        RuleFactory.makeProductRule(
            this.terms.get(0).getDerivative(), this.terms.get(1).getDerivative());
    if (this.negative) {
      toReturn.flipSign();
    }
    return toReturn;
  }

  ChainRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public double getResult(List<Integer> dims) {
    return this.terms.get(0).evaluate(dims);
  }

  @Override
  public String toString() {
    return ""; // TODO is this right?
  }
}
