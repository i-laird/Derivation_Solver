package calculator.rules;

import calculator.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.rules.RuleFactory.*;

public class ArcCotRule  extends TrigRule {

    ArcCotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return makeFracRule(new Term(-1), makeAdditionRule(new Term(1), makePowerRule(this.terms.get(0), new Term(2))));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
