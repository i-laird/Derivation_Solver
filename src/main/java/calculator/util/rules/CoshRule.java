package calculator.util.rules;

import calculator.util.terms.Term;

import static calculator.util.rules.RuleFactory.*;

import java.util.LinkedList;
import java.util.List;

public class CoshRule  extends TrigRule {

    CoshRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makeSinhRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.cosh(this.terms.get(0).evaluate(dims));
    }
}
