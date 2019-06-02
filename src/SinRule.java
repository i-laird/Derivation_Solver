import java.util.LinkedList;

public class SinRule extends DerivationRule {

    public SinRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeCosRule(this.terms.get(0));
    }
}
