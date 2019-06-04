import Terms.Term;

public class Main {
    public static void main(String[] args) {
        Parser p = new Parser(System.in);
        Term parsedStatement = p.getRoot();
    }
}
