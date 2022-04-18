package calculator.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.terms.Term;

import static calculator.rules.RuleFactory.*;
public class SecRule extends TrigRule {

    SecRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx sec(x) = sec(x) * tan(x)
        return makeProductRule(makeTanRule(this.terms.get(0)), makeSecRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.cos(this.terms.get(0).evaluate(dims));
    }
}
