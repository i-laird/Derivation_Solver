package Terms;

import Enums.AbstractMath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variable extends Term implements AbstractMath{

    public static Variable getVariable(char c){
        return new Variable(Var.getVariable(c));
    }


    //local variables
    Var v = null;

    public Variable(Var v) {
        this.v = v;
    }

    public Variable (char c){
        this.v = Var.getVariable(c);
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
