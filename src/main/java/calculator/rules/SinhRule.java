package calculator.rules;

import calculator.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.rules.RuleFactory.*;

public class SinhRule extends TrigRule {

    SinhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makeCoshRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.sinh(this.terms.get(0).evaluate(dims));
    }
}
