package Enums;

import Terms.Term;

public class Num implements AbstractMath {
    int num;
    public Num(int n){
        num = n;
    }
    public Term getTermFromOp(Term one, Term two){
        return new Term(this.num);
    }
}