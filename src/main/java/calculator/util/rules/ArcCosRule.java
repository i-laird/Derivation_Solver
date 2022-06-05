package calculator.util.rules;

import calculator.util.terms.Term;

import static calculator.util.rules.RuleFactory.*;

import java.util.LinkedList;
import java.util.List;

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
        return makeFracRule(new Term(-1),
                makePowerFracRule(
                        makeAdditionRule(new Term(1),
                                makePowerRule(this.t, new Term(2)).flipSign()),
                        new Term(1),
                        new Term(2)
                ));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
