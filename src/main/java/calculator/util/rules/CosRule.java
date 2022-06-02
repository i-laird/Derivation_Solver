package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.util.terms.Term;

import static calculator.util.rules.RuleFactory.*;

public class CosRule extends TrigRule {

    CosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "cos";
    }
    @Override
    public Term getDerivPart() {
        // d/dx cos(x) = -sin(x)
        return makeSinRule(this.terms.get(0)).flipSign();
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.cos(this.terms.get(0).evaluate(dims));
    }
}
