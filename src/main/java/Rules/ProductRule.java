package Rules;

import java.util.LinkedList;
import java.util.List;

import Terms.Term;


public class ProductRule extends DerivationRule {

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        //need to account for a variable number of terms that are being multipled
        if(original.size() != derived.size()){
            throw new RuntimeException("The sizes do not match");
        }
        LinkedList<Term> addTerms = new LinkedList<>();
        for(int i = 0; i < original.size(); i++){
            LinkedList<Term> multTerms = new LinkedList<>();
            for(int j = 0; j < original.size(); j++){
                if(i != j){
                    multTerms.add(original.get(j));
                }
                else{
                    multTerms.add(derived.get(j));
                }
            }
            addTerms.add(new ProductRule(multTerms));
        }
        return new AdditionRule(addTerms);

    }

    public ProductRule addTerm(Term t){
        this.terms.add(t);
        return this;
    }

    public ProductRule(LinkedList<Term> l) {
        super(l);
    }

    public int evaluate(List<Integer> dims){
        int toReturn = this.terms.stream().map(x -> x.evaluate(dims)).reduce(1, Math::multiplyExact);
        if(this.negative){
            toReturn = toReturn * -1;
        }
        return toReturn;
    }
}
