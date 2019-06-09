package Rules;

import java.util.LinkedList;
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
}
