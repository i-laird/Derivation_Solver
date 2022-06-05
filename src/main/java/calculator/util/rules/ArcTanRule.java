package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.util.rules.RuleFactory.*;

public class ArcTanRule extends TrigRule {

    ArcTanRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "tan-1";
    }
    @Override
    public Term getDerivPart() {
        return makeFracRule(new Term(1), makeAdditionRule(new Term(1), makePowerRule(this.t, new Term(2))));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
