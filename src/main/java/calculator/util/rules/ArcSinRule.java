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
    Term xSquared = makePowerRule(this.t, new Term(2));
    Term oneMinusXSquared = makeAdditionRule(new Term(1), xSquared.flipSign());
    Term sqrt = makePowerFracRule(oneMinusXSquared, new Term(1), new Term(2));
    return makeFracRule(new Term(1), sqrt);
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.asin(this.t.evaluate(dims));
  }
}
