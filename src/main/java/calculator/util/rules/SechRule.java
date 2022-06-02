package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.util.rules.RuleFactory.*;

public class SechRule extends TrigRule {

    SechRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "sech";
    }
    @Override
    public Term getDerivPart() {
        return makeProductRule(makeTanhRule(this.terms.get(0)).flipSign(), makeSechRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
