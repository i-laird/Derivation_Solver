package Rules;

import Terms.Term;

import java.util.LinkedList;

public class PowerFracRule extends DerivationRule {

    public PowerFracRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        int topPow = this.terms.get(1).getNum(),
            bottomPow = this.terms.get(2).getNum();
        Term base = this.terms.get(0);

        //if it is not zero we just do a simple multiplication
        if (topPow != 0 && bottomPow != 0){
            return rf.makeProductRule(rf.makeFracRule(new Term(topPow), new Term(bottomPow)), rf.makePowerFracRule(base, new Term(topPow - bottomPow), this.terms.get(2)));
        }

        return new Term(1);
    }
}

