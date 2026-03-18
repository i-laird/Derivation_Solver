package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

public final class PowerFracRule extends DerivationRule {

  PowerFracRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public Term getDerivative(char withRespectTo) {
    int topPow = this.terms.get(1).getNum(), bottomPow = this.terms.get(2).getNum();
    Term base = this.terms.get(0);

    // if it is not zero we just do a simple multiplication
    if (topPow != 0 && bottomPow != 0) {
      Term outerDerivative = makeProductRule(
          makeFracRule(new Term(topPow), new Term(bottomPow)),
          makePowerFracRule(base, new Term(topPow - bottomPow), this.terms.get(2)));
      return makeProductRule(outerDerivative, base.getDerivative(withRespectTo));
    }

    return new Term(0);
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    double base = this.terms.get(0).evaluate(dims);
    int topPow = this.terms.get(1).getNum();
    int bottomPow = this.terms.get(2).getNum();
    return Math.pow(base, (double) topPow / bottomPow);
  }

  @Override
  public String toString() {
    return this.terms.get(0)
        + " ^ ( "
        + this.terms.get(1).getNum()
        + " / "
        + this.terms.get(2).getNum()
        + " ) ";
  }
}
