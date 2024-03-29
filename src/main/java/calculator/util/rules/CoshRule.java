package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class CoshRule extends TrigRule {

  CoshRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "cosh";
  }

  @Override
  public Term getDerivPart() {
    return makeSinhRule(this.t);
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.cosh(this.t.evaluate(dims));
  }
}
