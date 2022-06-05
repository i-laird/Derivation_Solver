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
    public String functionName() {
        return "cosh";
    }
    @Override
    public Term getDerivPart() {
        return makeSinhRule(this.t);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.cosh(this.t.evaluate(dims));
    }
}
