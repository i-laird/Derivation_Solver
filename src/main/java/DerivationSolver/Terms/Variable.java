package DerivationSolver.Terms;

import DerivationSolver.Enums.AbstractMath;

import java.util.List;

public class Variable extends Term implements AbstractMath{

    //local variables
    private Character variableSymbol = null;

    public Variable (char c){
        this.variableSymbol = c;
    }

    @Override
    public Term getDerivative(){
        //the derivative of x with respect to x is simply 1
        return (!this.negative ? new Term(1) : new Term(-1));
    }

    public Term getTermFromOp(Term one, Term two){
        return this;
    }

    public double evaluate(List<Integer> dims){
        return (this.negative ? dims.get(0) * -1 : dims.get(0));
    }

}
