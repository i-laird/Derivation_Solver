package calculator.util.token;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import calculator.util.terms.Variable;

public enum Function implements AbstractMath {
  SIN,
  COSINE,
  TAN,
  SEC,
  CSC,
  COT,
  SINH,
  COSH,
  TANH,
  SECH,
  CSCH,
  COTH,
  ARCCOS,
  ARCSIN,
  ARCTAN,
  ARCSEC,
  ARCCSC,
  ARCCOT;

  public static Function getFunc(String s) {
    switch (s.toUpperCase()) {
      case "SIN":
        return SIN;
      case "COS":
        return COSINE;
      case "TAN":
        return TAN;
      case "SEC":
        return SEC;
      case "CSC":
        return CSC;
      case "COT":
        return COT;
      case "SINH":
        return SINH;
      case "COSH":
        return COSH;
      case "TANH":
        return TANH;
      case "SECH":
        return SECH;
      case "CSCH":
        return CSCH;
      case "COTH":
        return COTH;
      case "ARCCOS":
        return ARCCOS;
      case "ARCSIN":
        return ARCSIN;
      case "ARCTAN":
        return ARCTAN;
      case "ARCSEC":
        return ARCSEC;
      case "ARCCSC":
        return ARCCSC;
      case "ARCCOT":
        return ARCCOT;
    }
    return null;
  }

  public Term getTermFromOp(Term one, Term two) {
    Term toReturn = null;
    switch (this) {
      case SIN:
        toReturn = makeSinRule(one);
        break;
      case COSINE:
        toReturn = makeCosRule(one);
        break;
      case TAN:
        toReturn = makeTanRule(one);
        break;
      case SEC:
        toReturn = makeSecRule(one);
        break;
      case CSC:
        toReturn = makeCscRule(one);
        break;
      case COT:
        toReturn = makeCotRule(one);
        break;
      case SINH:
        toReturn = makeSinhRule(one);
        break;
      case COSH:
        toReturn = makeCoshRule(one);
        break;
      case TANH:
        toReturn = makeTanhRule(one);
        break;
      case SECH:
        toReturn = makeSechRule(one);
        break;
      case CSCH:
        toReturn = makeCschRule(one);
        break;
      case COTH:
        toReturn = makeCothRule(one);
        break;
      case ARCCOS:
        toReturn = makeArcCosRule(one);
        break;
      case ARCSIN:
        toReturn = makeArcSinRule(one);
        break;
      case ARCTAN:
        toReturn = makeArcTanRule(one);
        break;
      case ARCSEC:
        toReturn = null; // TODO
        break;
      case ARCCSC:
        toReturn = null; // TODO
        break;
      case ARCCOT:
        toReturn = makeArcCotRule(one);
        break;
      default:
        throw new RuntimeException("Invalid DerivationSolver.Enums function");
    }
    if (one.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, one);
    }
    return toReturn;
  }
}
