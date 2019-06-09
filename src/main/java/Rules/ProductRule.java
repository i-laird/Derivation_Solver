package Rules;

import java.util.LinkedList;
import java.util.List;

import Terms.Term;


public class ProductRule extends DerivationRule {

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        if (original.size() != 2 || derived.size() != 2){
            return null;
        }
        LinkedList<Term> p1 = new LinkedList<>(),
                         p2 = new LinkedList<>(),
                         result = new LinkedList<>();
        p1.add(original.get(0));
        p1.add(derived.get(1));

        p2.add(original.get(1));
        p2.add(derived.get(0));

        result.add(new ProductRule(p1));
        result.add(new ProductRule(p2));

        return new AdditionRule(result);

    }

    public ProductRule(LinkedList<Term> l) {
        super(l);
    }

    public int evaluate(List<Integer> dims){
        return this.terms.get(0).evaluate(dims) * this.terms.get(1).evaluate(dims);
    }
}
