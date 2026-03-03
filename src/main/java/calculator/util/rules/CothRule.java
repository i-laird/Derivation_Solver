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
    return makePowerRule(new Term(2), makeCschRule(this.t)).flipSign();
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return 0;
  }
}
