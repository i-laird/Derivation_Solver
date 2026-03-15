package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class ArcCscRule extends TrigRule {

  ArcCscRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "csc-1";
  }

  @Override
  public Term getDerivPart() {
    return createArcSecRule(this.t).getDerivPart().flipSign();
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.asin(1.0 / this.t.evaluate(dims));
  }
}
