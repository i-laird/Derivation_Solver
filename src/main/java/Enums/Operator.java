package Enums;

import Rules.RuleFactory;
import Terms.Term;
import Terms.Variable;

public enum Operator implements AbstractMath{
    ADD(1, Associativity.LEFT), SUBTRACT(1, Associativity.LEFT), MULTIPLY(2, Associativity.LEFT), DIVIDE(2, Associativity.LEFT), EXPONENT(3, Associativity.RIGHT), LOG(3, Associativity.RIGHT), NAT_LOG(3, Associativity.RIGHT);
    public static enum Associativity{
        RIGHT, LEFT
    }
    public int precedence;
    public Associativity associativity;

    Operator(int p, Associativity a){
        precedence = p;
        associativity = a;
    }

    public static Operator getOp(String s){
        switch(s){
            case "ln":
                return NAT_LOG;
            case "log":
                return LOG;
            case "+":
                return ADD;
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "^":
                return EXPONENT;
        }
        return null;
    }

    public Term getTermFromOp(Term one, Term two){
        Term toReturn = null;
        boolean specialCase = false;
        switch(this) {
            case NAT_LOG:
                toReturn = rf.makeNaturalLogRule(one);
                specialCase = true;
                break;
            case LOG:
                toReturn = rf.makeLogRule(one, two);
                specialCase = true;
                break;
            case ADD:
                toReturn = rf.makeAdditionRule(one, two);
                break;
            case SUBTRACT:
                toReturn = rf.makeAdditionRule(one, two.flipSign());
                break;
            case MULTIPLY:
                toReturn = rf.makeProductRule(one, two);
                break;
            case DIVIDE:
                toReturn = rf.makeFracRule(one, two);
                break;
            case EXPONENT:
                toReturn = rf.makePowerRule(one, two);
                specialCase = true;
                break;
            default:
                throw new RuntimeException("Invalid Enums.Operator");
        }
            // see if the rule needs to be wrapped in a chain rule
            // if it is just one variable inside there is no need for the chain rule because of the implied one
            if(specialCase && one.getClass() != Variable.class){
                toReturn = rf.makeChainRule(toReturn, one);
            }
            return toReturn;
    }
}
