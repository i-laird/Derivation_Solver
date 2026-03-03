package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class ArcCosRule extends TrigRule {

  ArcCosRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "cos-1";
  }

  @Override
  public Term getDerivPart() {
    return makeArcSinRule(this.t).getDerivPart().flipSign();
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.acos(this.t.evaluate(dims));
  }
}
