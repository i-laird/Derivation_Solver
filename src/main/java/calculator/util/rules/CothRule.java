/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

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
  public double getResult(ImmutableList<Integer> dims) {
    return 0;
  }
}
