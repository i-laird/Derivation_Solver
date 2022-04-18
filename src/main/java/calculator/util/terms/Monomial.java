package calculator.util.terms;

import calculator.util.rules.DerivationRule;

import java.util.List;

public class Monomial extends DerivationRule {
    Term coefficient;
    Term variable;
    int pow;

    Monomial(Term coefficient, Variable v, int pow){
        this.coefficient = coefficient;
        this.variable = v;
        this.pow = pow;
    }

    @Override
    public Term getDerivative() {
        return new Monomial(new Term(this.coefficient.getNum() * this.pow), (Variable)this.variable, pow != 0 ? pow - 1 : pow );
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }

}
