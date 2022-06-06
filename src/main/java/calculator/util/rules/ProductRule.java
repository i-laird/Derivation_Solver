package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import calculator.util.Parser;
import calculator.util.terms.Term;

/**
 * PRODUCT RULE
 *
 * this is used when multiple terms are multiplied together.
 *
 * This class supports a product rule of more than two terms but {@link Parser}
 * will not construct AST that have more than two kid
 */
public final class ProductRule extends DerivationRule {

    /**
     * d/dx( f(x) * g(x) ) = f(x) * g'(x) + f'(x) * g(x)
     */
    @Override
    public Term getDerivative() {
        LinkedList<Term> addTerms = new LinkedList<>();
        for(int i = 0; i < this.terms.size(); i++){
            LinkedList<Term> multTerms = new LinkedList<>();
            for(int j = 0; j < this.terms.size(); j++){
                if(i != j){
                    multTerms.add(this.terms.get(j));
                }
                else{
                    multTerms.add(this.terms.get(j).getDerivative());
                }
            }
            addTerms.add(new ProductRule(multTerms));
        }
        AdditionRule toReturn = new AdditionRule(addTerms);
        if(this.negative){
            toReturn.flipSign();
        }
        return toReturn;
    }

    ProductRule(LinkedList<Term> l) {
        super(l);
    }

    /**
     * Adds a term to the product Rule
     * @param t the term to add
     * @return this
     */
    public ProductRule addTerm(Term t){
        this.terms.add(t);
        return this;
    }

    @Override
    public double getResult(List<Integer> dims) {
        return this.terms.stream().map(x -> x.evaluate(dims)).reduce((x,y) -> x*y).get();
    }

    @Override
    public String toString() {
        return terms.stream().map(Object::toString).collect(Collectors.joining(" * "));
    }

}
