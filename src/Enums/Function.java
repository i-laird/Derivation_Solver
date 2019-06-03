package Enums;

import Terms.Term;

import static Enums.AbstractMath.rf;

public enum Function implements AbstractMath {
    SIN, COSINE, TAN, SEC, CSC, COT, SINH, COSH, TANH, SECH, CSCH, COTH, ARCCOS, ARCSIN, ARCTAN, ARCSEC, ARCCSC, ARCCOT;
    public static  Function getFunc(String s){
        switch(s.toUpperCase()){
            case "SIN":
                return SIN;
            case "COSINE":
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
        switch(this){
            case SIN:
                return rf.makeSinRule(one);
            case COSINE:
                return rf.makeCosRule(one);
            case TAN:
                return rf.makeTanRule(one);
            case SEC:
                return rf.makeSecRule(one);
            case CSC:
                return rf.makeCscRule(one);
            case COT:
                return rf.makeCotRule(one);
            case SINH:
                return rf.makeSinhRule(one);
            case COSH:
                return rf.makeCoshRule(one);
            case TANH:
                return rf.makeTanhRule(one);
            case SECH:
                return rf.makeSechRule(one);
            case CSCH:
                return rf.makeCschRule(one);
            case COTH:
                return rf.makeCothRule(one);
            case ARCCOS:
                return rf.makeArcCosRule(one);
            case ARCSIN:
                return rf.makeArcSinRule(one);
            case ARCTAN:
                return rf.makeArcTanRule(one);
            case ARCSEC:
                return null; //TODO
            case ARCCSC:
                return null; //TODO
            case ARCCOT:
                return rf.makeArcCotRule(one);
        }
        throw new RuntimeException("Invalid Enums function");
    }

}
