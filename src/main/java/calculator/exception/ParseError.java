package calculator.exception;

public class ParseError extends RuntimeException{

    public ParseError(){
        super();
    }

    public ParseError(String m){
        super(m);
    }
}
