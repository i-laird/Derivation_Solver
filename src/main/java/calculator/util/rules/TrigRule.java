package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;

public abstract class TrigRule extends DerivationRule {

    @Override
    public Term getDerivative() {
        Term t = this.getDerivPart();
        if(this.negative){
            t.flipSign();
        }
        return t;
    }

    public abstract Term getDerivPart();

    public TrigRule(LinkedList<Term > l){super(l);}

    @Override
    public String toString(){
        return functionName() + " ( " + terms.get(0) + " ) ";
    }

    /**
     * Function name that is to be used in the tostring representation.
     *
     * Assumptions:
     *  function name must not have a space in it
     *  function name must match up with the name used for parsing
     * @return function name
     */
    public abstract String functionName();
}
