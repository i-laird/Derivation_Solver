package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import java.util.LinkedList;
import java.util.Map;

public final class CschRule extends TrigRule {

  CschRule(LinkedList<Term> l) {
    super(l);
  }

  @Override
  public String functionName() {
    return "csch";
  }

  @Override
  public Term getDerivPart() {
    return makeProductRule(makeCothRule(this.t).flipSign(), makeCschRule(this.t));
  }

  @Override
  public double getResult(Map<Character, Integer> dims) {
    return 0;
  }
}
