package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.util.rules.RuleFactory.*;

public final class PowerFracRule extends DerivationRule {

    PowerFracRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        int topPow = this.terms.get(1).getNum(),
            bottomPow = this.terms.get(2).getNum();
        Term base = this.terms.get(0);

        //if it is not zero we just do a simple multiplication
        if (topPow != 0 && bottomPow != 0){
            return makeProductRule(makeFracRule(new Term(topPow), new Term(bottomPow)), makePowerFracRule(base, new Term(topPow - bottomPow), this.terms.get(2)));
        }

        return new Term(1);
    }

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived) {
        return null;
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }

    @Override
    public String toString() {
        return this.terms.get(0) + " ^ ( " + this.terms.get(1).getNum() + " / " + this.terms.get(2).getNum() + " ) ";
    }
}

