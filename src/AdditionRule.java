import java.util.LinkedList;

public class AdditionRule extends DerivationRule {
    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        return new AdditionRule(derived);
    }

    public AdditionRule(LinkedList<Term> l) {
        super(l);
    }
}
