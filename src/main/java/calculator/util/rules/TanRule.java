package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class TanRule extends TrigRule {

  TanRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "tan";
  }

  @Override
  public Term getDerivPart() {
    // d/dx tan(x) = sec(x)^2
    return makePowerRule(new Term(2), makeSecRule(this.t));
  }

  @Override
  public double getResult(List<Integer> dims) {
    return Math.tan(this.t.evaluate(dims));
  }
}
