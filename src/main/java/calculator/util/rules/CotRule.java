package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class CotRule extends TrigRule {

  CotRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "cot";
  }

  @Override
  public Term getDerivPart() {
    // d/dx cot(x) = -csc(x)^2
    return makePowerRule(makeCscRule(this.t), new Term(2).flipSign());
  }

  @Override
  public double getResult(List<Integer> dims) {
    return 1.0 / Math.tan(this.t.evaluate(dims));
  }
}
