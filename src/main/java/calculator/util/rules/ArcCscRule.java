package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

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
    return makeArcSecRule(this.t).getDerivPart().flipSign();
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    return Math.asin(1.0 / this.t.evaluate(dims));
  }
}
