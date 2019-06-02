import java.util.LinkedList;

public class CscRule extends DerivationRule {

    public CscRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx csc(x) = -csc(x) * cot(x)
        return rf.makeProductRule(rf.makeCscRule(this.terms.get(0)).flipSign(), rf.makeCotRule(this.terms.get(0)));
    }
}
