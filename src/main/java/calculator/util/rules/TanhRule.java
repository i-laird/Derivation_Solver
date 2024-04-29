package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class TanhRule extends TrigRule {

  TanhRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "tanh";
  }

  @Override
  public Term getDerivPart() {
    return makePowerRule(new Term(2), makeSechRule(this.t));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.tanh(this.t.evaluate(dims));
  }
}
