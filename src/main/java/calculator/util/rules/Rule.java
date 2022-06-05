package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.List;

public abstract sealed class Rule extends Term permits DerivationRule, TrigRule {

    /**
     * gets the result of the expression using {@link calculator.util.rules.DerivationRule#getResult(List)}
     * and negates it if necessary.
     *
     * @param dims values to use for the variables
     * @return the result
     */
    public double evaluate(List<Integer> dims){
        double toReturn = this.getResult(dims);
        if(this.negative){
            toReturn = toReturn * -1;
        }
        return toReturn;
    }

    /**
     * getResult
     *
     * returns the result after evaluating the expression. Does not account for the negative flag and that is why this
     * method is not intended to be called publicly.
     *
     * @param dims values to use for the variables
     * @return evaluated expression for dims
     */
    abstract double getResult(List<Integer> dims);
}
