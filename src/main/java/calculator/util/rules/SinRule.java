/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class SinRule extends TrigRule {

  SinRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "sin";
  }

  @Override
  public Term getDerivPart() {
    return makeCosRule(this.t);
  }

  @Override
  public double getResult(List<Integer> dims) {
    return Math.sin(this.t.evaluate(dims));
  }
}
