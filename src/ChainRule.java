import java.util.LinkedList;

public class ChainRule extends DerivationRule {

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        if (original.size() != 2 || derived.size() != 2){
            return null;
        }

        return rf.makeProductRule(derived.get(0), derived.get(1));
    }

    public ChainRule(LinkedList<Term> l) {
        super(l);
    }
}
