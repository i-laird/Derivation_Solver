package Terms;

import java.util.List;

public class Term {
    private int num;
    protected boolean negative = false;

    public int getNum() {
        return num * (negative ? -1 : 1);
    }

    public void setNum(int num) {
        this.num = num;
        this.negative = false;
    }

    public Term(int num) {
        this.num = num;
    }

    public Term(){}

    public Term getDerivative(){
        //in this case it is simply a number
        return new Term(0);
    }
    public Term flipSign(){
        negative = !negative;
        return this;
    }

    public int evaluate(List<Integer> dims){
        return this.getNum();
    }
}
