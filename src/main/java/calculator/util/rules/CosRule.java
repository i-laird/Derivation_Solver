package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class CosRule extends TrigRule {

  CosRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "cos";
  }

  @Override
  public Term getDerivPart() {
    // d/dx cos(x) = -sin(x)
    return makeSinRule(this.t).flipSign();
  }

  @Override
  public double getResult(List<Integer> dims) {
    return Math.cos(this.t.evaluate(dims));
  }
}
