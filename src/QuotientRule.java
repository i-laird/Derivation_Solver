import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class QuotientRule extends DerivationRule {
    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        if (original.size() != 2 || derived.size() != 2){
            return null;
        }
        LinkedList<Term> p1 = new LinkedList<>(),
                p2 = new LinkedList<>(),
                subtraction = new LinkedList<>(),
                quotient = new LinkedList<>();

        p1.add(derived.get(0));
        p1.add(original.get(1));

        p2.add(derived.get(0));
        p2.add(original.get(1));

        subtraction.add(new ProductRule(p1));
        subtraction.add(new ProductRule(p2).flipSign());

        quotient.add(new AdditionRule(subtraction));

        quotient.add(
                new ChainRule(new LinkedList<Term>(Arrays.asList(
                    original.get(1),
                    new PowerRule(new LinkedList<Term>(Arrays.asList(
                            new Term(2)
                    )))
                )))
        );

        return new QuotientRule(quotient);
        }

    public QuotientRule(LinkedList<Term> l) {
        super(l);
    }
}
