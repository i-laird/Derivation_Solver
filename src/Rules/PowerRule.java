package Rules;

import java.util.LinkedList;
import Terms.Term;


public class PowerRule extends DerivationRule {
    public PowerRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        int pow = this.terms.get(1).getNum();
        Term base = this.terms.get(0);

        //if it is not zero we just do a simple multiplication
        if (pow != 0){
            return rf.makeProductRule(new Term(pow - 1), rf.makePowerRule(base, new Term(pow - 1)));
        }

        return new Term(1);
    }
}
