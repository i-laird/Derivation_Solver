package DerivationSolver.Rules;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Terms.Term;


public class QuotientRule extends DerivationRule {

    public final static int NUMERATOR_POS = 1;
    public final static int DENOM_POS = 0;

    // NUMERATOR is the second term and DENOMINATOR is the first
    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        if (original.size() != 2 || derived.size() != 2){
            return null;
        }
        LinkedList<Term> p1 = new LinkedList<>(),
                p2 = new LinkedList<>(),
                subtraction = new LinkedList<>(),
                quotient = new LinkedList<>();

        p1.add(derived.get(NUMERATOR_POS));
        p1.add(original.get(DENOM_POS));

        p2.add(derived.get(DENOM_POS));
        p2.add(original.get(NUMERATOR_POS));

        subtraction.add(new ProductRule(p1));
        subtraction.add(new ProductRule(p2).flipSign());

        // the denom gets added first
        quotient.add(
                new ChainRule(new LinkedList<Term>(Arrays.asList(
                    new PowerRule(new LinkedList<Term>(Arrays.asList(
                            new Term(2),
                            original.get(DENOM_POS)
                    ))),
                    original.get(DENOM_POS)
                )))
        );

        // then add the numerator
        quotient.add(new AdditionRule(subtraction));

        return new QuotientRule(quotient);
    }

    public QuotientRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return this.terms.get(NUMERATOR_POS).evaluate(dims) / this.terms.get(DENOM_POS).evaluate(dims);
    }
}
