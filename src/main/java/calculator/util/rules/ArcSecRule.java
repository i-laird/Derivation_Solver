package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

public final class ArcSecRule extends TrigRule {

  ArcSecRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "sec-1";
  }

  @Override
  public Term getDerivPart() {
    return makeFracRule(
        makeProductRule(
            this.t,
            makePowerFracRule(
                makeAdditionRule(makePowerRule(new Term(2), this.t), new Term(1).flipSign()),
                new Term(1),
                new Term(2))),
        new Term(1));
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    return Math.acos(1.0 / this.t.evaluate(dims));
  }
}
