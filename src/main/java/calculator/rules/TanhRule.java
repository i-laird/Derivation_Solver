package calculator.rules;

import calculator.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.rules.RuleFactory.*;

public class TanhRule extends TrigRule {

    TanhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makePowerRule(new Term(2), makeSechRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.tanh(this.terms.get(0).evaluate(dims));
    }
}
