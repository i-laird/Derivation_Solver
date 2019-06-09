package Rules;

import Terms.Term;

import java.util.LinkedList;

public class ChainRule extends DerivationRule {

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        if (original.size() != 2 || derived.size() != 2){
            return null;
        }

        ProductRule toReturn = rf.makeProductRule(derived.get(0), derived.get(1));
        if(this.negative){
            toReturn.flipSign();
        }
        return toReturn;
    }

    public ChainRule(LinkedList<Term> l) {
        super(l);
    }
}
