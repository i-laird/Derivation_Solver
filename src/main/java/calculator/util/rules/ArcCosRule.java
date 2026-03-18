package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

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
  public double getResult(Map<Character, Integer> dims) {
    return Math.acos(this.t.evaluate(dims));
  }
}
