package calculator.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.terms.Term;

import static calculator.rules.RuleFactory.*;

public class CotRule extends TrigRule {

    CotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx cot(x) = -csc(x)^2
        return makePowerRule(makeCscRule(this.terms.get(0)), new Term(2).flipSign());
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.tan(this.terms.get(0).evaluate(dims));
    }
}
