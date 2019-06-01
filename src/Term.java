public class Term {
    private int num;
    protected boolean negative = false;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
        negative = negative ? false : true;
        return this;
    }
}
