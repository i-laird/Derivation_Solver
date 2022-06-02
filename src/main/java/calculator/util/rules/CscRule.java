package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.util.terms.Term;

import static calculator.util.rules.RuleFactory.*;

public class CscRule extends TrigRule {

    CscRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "csc";
    }
    @Override
    public Term getDerivPart() {
        // d/dx csc(x) = -csc(x) * cot(x)
        return makeProductRule(makeCscRule(this.terms.get(0)).flipSign(), makeCotRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.sin(this.terms.get(0).evaluate(dims));
    }
}
