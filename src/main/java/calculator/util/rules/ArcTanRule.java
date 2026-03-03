package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class ArcTanRule extends TrigRule {

  ArcTanRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "tan-1";
  }

  @Override
  public Term getDerivPart() {
    return makeFracRule(
        makeAdditionRule(new Term(1), makePowerRule(new Term(2), this.t)), new Term(1));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.atan(this.t.evaluate(dims));
  }
}
