import java.util.LinkedList;

public class CosRule extends DerivationRule {

    public CosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx cos(x) = -sin(x)
        return rf.makeSinRule(this.terms.get(0)).flipSign();
    }
}
