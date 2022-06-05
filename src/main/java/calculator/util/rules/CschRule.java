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
    public String functionName() {
        return "csch";
    }
    @Override
    public Term getDerivPart() {
        return makeProductRule(makeCotRule(this.t).flipSign(), makeCschRule(this.t));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
