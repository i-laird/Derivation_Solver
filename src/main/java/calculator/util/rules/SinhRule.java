package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.util.rules.RuleFactory.*;

public class SinhRule extends TrigRule {

    SinhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "sinh";
    }
    @Override
    public Term getDerivPart() {
        return makeCoshRule(this.t);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.sinh(this.t.evaluate(dims));
    }
}
