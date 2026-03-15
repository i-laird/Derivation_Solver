package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class ArcCotRule extends TrigRule {

  ArcCotRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "cot-1";
  }

  @Override
  public Term getDerivPart() {
    return createArcTanRule(this.t).getDerivPart().flipSign();
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.PI / 2.0 - Math.atan(this.t.evaluate(dims));
  }
}
