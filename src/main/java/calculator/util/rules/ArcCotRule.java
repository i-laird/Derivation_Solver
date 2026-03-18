package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

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
    return makeArcTanRule(this.t).getDerivPart().flipSign();
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    return Math.PI / 2.0 - Math.atan(this.t.evaluate(dims));
  }
}
