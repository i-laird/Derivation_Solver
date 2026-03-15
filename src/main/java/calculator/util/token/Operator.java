package calculator.util.token;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.rules.AdditionRule;
import calculator.util.rules.ProductRule;
import calculator.util.terms.Term;
import calculator.util.terms.Variable;

public enum Operator implements AbstractMath {
  ADD(1, Associativity.LEFT),
  SUBTRACT(1, Associativity.LEFT),
  MULTIPLY(2, Associativity.LEFT),
  DIVIDE(2, Associativity.LEFT),
  EXPONENT(3, Associativity.RIGHT),
  LOG(3, Associativity.RIGHT),
  NAT_LOG(3, Associativity.RIGHT);

  public int precedence;
  public Associativity associativity;

  Operator(int p, Associativity a) {
    precedence = p;
    associativity = a;
  }

  public static Operator getOp(String s) {
    switch (s) {
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

  public Term getTermFromOp(Term one, Term two) {
    Term toReturn = null;
    switch (this) {
      case NAT_LOG:
        toReturn = makeNaturalLogRule(two);
        break;
      case LOG:
        toReturn = makeLogRule(one, two);
        break;
      case SUBTRACT:
        one.flipSign();
      case ADD:
        // see if an addition is being made to an existing addtion
        if (one.getClass() == AdditionRule.class) {
          toReturn = ((AdditionRule) one).addTerm(two);
        } else if (two.getClass() == AdditionRule.class) {
          toReturn = ((AdditionRule) two).addTerm(one);
        }

        // if not create a new addition
        else {
          toReturn = makeAdditionRule(one, two);
        }
        break;
      case MULTIPLY:
        // see if an mult is being made to an existing mult
        if (one.getClass() == ProductRule.class) {
          toReturn = ((ProductRule) one).addTerm(two);
        } else if (two.getClass() == ProductRule.class) {
          toReturn = ((ProductRule) two).addTerm(one);
        }

        // if not create a new mult
        else {
          toReturn = makeProductRule(one, two);
        }
        break;
      case DIVIDE:
        toReturn = makeFracRule(one, two);
        break;
      case EXPONENT:
        toReturn = makePowerRule(one, two);
        break;
      default:
        throw new IllegalStateException("Invalid DerivationSolver.Enums.Operator");
    }
    return toReturn;
  }

  public enum Associativity {
    RIGHT,
    LEFT
  }
}
