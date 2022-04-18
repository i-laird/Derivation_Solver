package calculator.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.terms.Term;

import static calculator.rules.RuleFactory.*;
public class TanRule extends TrigRule {

    TanRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx tan(x) = sec(x)^2
        return makePowerRule(new Term(2), makeSecRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.tan(this.terms.get(0).evaluate(dims));
    }
}
