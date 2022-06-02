package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.util.rules.RuleFactory.*;

public class CothRule  extends TrigRule {

    CothRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public String functionName() {
        return "coth";
    }
    @Override
    public Term getDerivPart() {
        return makeAdditionRule(new Term(1), makePowerRule(makeCothRule(this.terms.get(0)), new Term(2)).flipSign());
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
