import java.util.LinkedList;

public class NaturalLogRule extends DerivationRule {
    public NaturalLogRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        Term inside = this.terms.get(0);
        return rf.makeFracRule(new Term(1), inside);
    }
}
