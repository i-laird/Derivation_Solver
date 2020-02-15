package DerivationSolver.Enums;

import DerivationSolver.Terms.Term;
import DerivationSolver.Terms.Variable;

public enum Function implements AbstractMath {
    SIN, COSINE, TAN, SEC, CSC, COT, SINH, COSH, TANH, SECH, CSCH, COTH, ARCCOS, ARCSIN, ARCTAN, ARCSEC, ARCCSC, ARCCOT;
    public static  Function getFunc(String s){
        switch(s.toUpperCase()){
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
    public Term getTermFromOp(Term one, Term two){
        Term toReturn = null;
        switch(this){
            case SIN:
                toReturn =  rf.makeSinRule(one);
                break;
            case COSINE:
                toReturn =  rf.makeCosRule(one);
                break;
            case TAN:
                toReturn =  rf.makeTanRule(one);
                break;
            case SEC:
                toReturn =  rf.makeSecRule(one);
                break;
            case CSC:
                toReturn =  rf.makeCscRule(one);
                break;
            case COT:
                toReturn =  rf.makeCotRule(one);
                break;
            case SINH:
                toReturn =  rf.makeSinhRule(one);
                break;
            case COSH:
                toReturn =  rf.makeCoshRule(one);
                break;
            case TANH:
                toReturn =  rf.makeTanhRule(one);
                break;
            case SECH:
                toReturn =  rf.makeSechRule(one);
                break;
            case CSCH:
                toReturn =  rf.makeCschRule(one);
                break;
            case COTH:
                toReturn =  rf.makeCothRule(one);
                break;
            case ARCCOS:
                toReturn =  rf.makeArcCosRule(one);
                break;
            case ARCSIN:
                toReturn =  rf.makeArcSinRule(one);
                break;
            case ARCTAN:
                toReturn =  rf.makeArcTanRule(one);
                break;
            case ARCSEC:
                toReturn =  null; //TODO
                break;
            case ARCCSC:
                toReturn =  null; //TODO
                break;
            case ARCCOT:
                toReturn =  rf.makeArcCotRule(one);
                break;
            default:
                throw new RuntimeException("Invalid DerivationSolver.Enums function");
        }
        if(one.getClass() != Variable.class) {
            toReturn = rf.makeChainRule(toReturn, one);
        }
        return toReturn;
    }

}
