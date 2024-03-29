/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class CscRule extends TrigRule {

  CscRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "csc";
  }

  @Override
  public Term getDerivPart() {
    // d/dx csc(x) = -csc(x) * cot(x)
    return makeProductRule(makeCscRule(this.t).flipSign(), makeCotRule(this.t));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return 1.0 / Math.sin(this.t.evaluate(dims));
  }
}
