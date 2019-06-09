package Rules;

import java.util.LinkedList;
import java.util.List;

import Terms.Term;


public abstract class DerivationRule extends Term {
    protected LinkedList<Term> terms;
    protected static final RuleFactory rf = RuleFactory.getFactory();
    public DerivationRule(LinkedList<Term> l) {
        this.terms = l;
    }

    public DerivationRule(){}
    @Override
    public Term getDerivative() {
        LinkedList<Term> derived = new LinkedList<>();
        terms.forEach(x -> derived.add(x.getDerivative()));
        return putTogether(terms, derived);
    }

    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        return null;
    }

    public double evaluate(List<Integer> dims){
        double toReturn = this.getResult(dims);
        if(this.negative){
            toReturn = toReturn * -1;
        }
        return toReturn;
    }

    public abstract double getResult(List<Integer> dims);
}
