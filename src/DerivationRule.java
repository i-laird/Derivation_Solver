import java.util.LinkedList;
import java.util.List;

public abstract class DerivationRule extends Term {
    protected LinkedList<Term> terms;

    public DerivationRule(LinkedList<Term> l) {
        this.terms = l;
    }

    public DerivationRule(){}
    @Override
    public Term getDerivative() {
        LinkedList<Term> derived = new LinkedList<>();
        terms.forEach(x -> derived.push(x.getDerivative()));
        return putTogether(terms, derived);
    }

    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        return null;
    }
}
