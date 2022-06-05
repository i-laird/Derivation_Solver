package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.util.terms.Term;

import static calculator.util.rules.RuleFactory.*;
public final class SecRule extends TrigRule {

    SecRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "sec";
    }
    @Override
    public Term getDerivPart() {
        // d/dx sec(x) = sec(x) * tan(x)
        return makeProductRule(makeTanRule(this.t), makeSecRule(this.t));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.cos(this.t.evaluate(dims));
    }
}
