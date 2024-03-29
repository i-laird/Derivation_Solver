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
        new Term(1), makeAdditionRule(new Term(1), makePowerRule(this.t, new Term(2))));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return 0;
  }
}
