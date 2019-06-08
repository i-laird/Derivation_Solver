package Rules;

import java.util.LinkedList;
import java.util.List;

import Terms.Term;


public class AdditionRule extends DerivationRule {
    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        return new AdditionRule(derived);
    }

    public AdditionRule(LinkedList<Term> l) {
        super(l);
    }

    public AdditionRule addTerm(Term t){
        this.terms.add(t);
        return this;
    }

    public int evaluate(List<Integer> dims){
        return this.terms.stream().map(x -> x.evaluate(dims)).reduce(0, Integer::sum);
    }
}
