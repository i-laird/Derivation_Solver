/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class SecRule extends TrigRule {

  SecRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "sec";
  }

  @Override
  public Term getDerivPart() {
    // d/dx sec(x) = sec(x) * tan(x)
    return makeProductRule(makeTanRule(this.t), makeSecRule(this.t));
  }

  @Override
  public double getResult(List<Integer> dims) {
    return 1.0 / Math.cos(this.t.evaluate(dims));
  }
}
