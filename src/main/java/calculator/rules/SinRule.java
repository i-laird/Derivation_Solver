package calculator.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.terms.Term;

import static calculator.rules.RuleFactory.*;
public class SinRule extends TrigRule {

    SinRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makeCosRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.sin(this.terms.get(0).evaluate(dims));
    }
}
