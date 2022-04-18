package calculator.rules;

import calculator.terms.Term;

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
}
