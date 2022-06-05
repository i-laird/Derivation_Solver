package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.util.rules.RuleFactory.*;

public final class TanhRule extends TrigRule {

    TanhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "tanh";
    }
    @Override
    public Term getDerivPart() {
        return makePowerRule(new Term(2), makeSechRule(this.t));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.tanh(this.t.evaluate(dims));
    }
}
