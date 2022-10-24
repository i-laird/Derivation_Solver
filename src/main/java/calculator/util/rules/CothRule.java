/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.List;

public final class CothRule extends TrigRule {

  CothRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "coth";
  }

  @Override
  public Term getDerivPart() {
    return makeAdditionRule(
        new Term(1), makePowerRule(makeCothRule(this.t), new Term(2)).flipSign());
  }

  @Override
  public double getResult(List<Integer> dims) {
    return 0;
  }
}
