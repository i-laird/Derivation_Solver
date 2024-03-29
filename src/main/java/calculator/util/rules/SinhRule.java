package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class SinhRule extends TrigRule {

  SinhRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "sinh";
  }

  @Override
  public Term getDerivPart() {
    return makeCoshRule(this.t);
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.sinh(this.t.evaluate(dims));
  }
}
