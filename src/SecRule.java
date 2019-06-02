import java.util.LinkedList;

public class SecRule extends DerivationRule {

    public SecRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx sec(x) = sec(x) * tan(x)
        return rf.makeProductRule(rf.makeTanRule(this.terms.get(0)), rf.makeSecRule(this.terms.get(0)));
    }
}
