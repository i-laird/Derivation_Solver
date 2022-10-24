/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class SechRule extends TrigRule {

  SechRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "sech";
  }

  @Override
  public Term getDerivPart() {
    return makeProductRule(makeTanhRule(this.t).flipSign(), makeSechRule(this.t));
  }

  @Override
  public double getResult(List<Integer> dims) {
    return 0;
  }
}
