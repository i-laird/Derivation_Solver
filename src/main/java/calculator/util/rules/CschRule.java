package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

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
    return makeProductRule(makeCotRule(this.t).flipSign(), makeCschRule(this.t));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return 0;
  }
}
