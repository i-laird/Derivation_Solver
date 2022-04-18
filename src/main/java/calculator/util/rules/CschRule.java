package calculator.util.rules;

import calculator.util.terms.Term;

import static calculator.util.rules.RuleFactory.*;

import java.util.LinkedList;
import java.util.List;

public class CschRule  extends TrigRule {

    CschRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makeProductRule(makeCotRule(this.terms.get(0)).flipSign(), makeCschRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
