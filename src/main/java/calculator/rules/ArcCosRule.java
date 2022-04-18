package calculator.rules;

import calculator.terms.Term;

import static calculator.rules.RuleFactory.*;

import java.util.LinkedList;
import java.util.List;

public class ArcCosRule extends TrigRule {

    ArcCosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makeFracRule(new Term(-1),
                makePowerFracRule(
                        makeAdditionRule(new Term(1),
                                makePowerRule(this.terms.get(0), new Term(2)).flipSign()),
                        new Term(1),
                        new Term(2)
                ));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
