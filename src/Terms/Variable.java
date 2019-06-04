package Terms;

import Enums.AbstractMath;

import java.util.HashMap;
import java.util.Map;

public class Variable extends Term implements AbstractMath {
    public static Variable getVariable(char c){
        return vars.get(c);
    }
    public static void declareVariable(char c){
        Variable v = new Variable(c);
        if(primaryVariable == null){
            primaryVariable = v;
        }
        vars.put(c, v);
    }

    //static variables
    static Map<Character, Variable> vars = new HashMap<>();
    static Variable primaryVariable = null;

    //local variables
    char c;

    public Variable(char c) {
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    @Override
    public Term getDerivative(){
        if(this.c == primaryVariable.c){
            //the derivative of x with respect to x is simply 1
            return new Term(1);
        }
        //in this case we are dealing with multivariable TODO
        return null;
    }

    public Term getTermFromOp(Term one, Term two){
        return this;
    }
}
