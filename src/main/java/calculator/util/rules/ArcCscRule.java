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
    return makeFracRule(
        makeProductRule(
            this.t,
            makePowerFracRule(
                makeAdditionRule(makePowerRule(this.t, new Term(2)), new Term(1).flipSign()),
                new Term(1),
                new Term(2))),
        new Term(-1));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return 0;
  }
}
