import java.util.LinkedList;

public class CotRule extends DerivationRule {

    public CotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx cot(x) = -csc(x)^2
        return rf.makePowerRule(rf.makeCscRule(this.terms.get(0)), new Term(2).flipSign());
    }
}
