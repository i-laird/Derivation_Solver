package calculator.rules;

import calculator.terms.Term;

import java.util.LinkedList;
import java.util.List;
import static calculator.rules.RuleFactory.*;

public class ChainRule extends DerivationRule {

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        if (original.size() != 2 || derived.size() != 2){
            return null;
        }

        ProductRule toReturn = makeProductRule(derived.get(0), derived.get(1));
        if(this.negative){
            toReturn.flipSign();
        }
        return toReturn;
    }

    ChainRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return this.terms.get(0).evaluate(dims);
    }
}
