package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class ArcSinRule extends TrigRule {

  ArcSinRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "sin-1";
  }

  @Override
  public Term getDerivPart() {
    return makeFracRule(
        makePowerFracRule(
            makeAdditionRule(new Term(1), makePowerRule(new Term(2), this.t).flipSign()),
            new Term(1),
            new Term(2)),
        new Term(1));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.asin(this.t.evaluate(dims));
  }
}
