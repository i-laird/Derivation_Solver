import java.util.LinkedList;

public class TanRule extends DerivationRule {

    public TanRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx tan(x) = sec(x)^2
        return rf.makePowerRule(rf.makeSecRule(this.terms.get(0)), new Term(2));
    }
}
