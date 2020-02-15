package DerivationSolver.Terms;

import java.util.HashMap;
import java.util.Map;

public class Var{
    public static Var getVariable(char c){
        declareVariable(c);
        return vars.get(c);
    }
    public static void declareVariable(char c){
        if (!vars.containsKey(c)) {
            Var v = new Var(c);
            if (primaryVariable == null) {
                primaryVariable = v;
            }
            vars.put(c, v);
        }
    }

    public static void reset(){
        vars = new HashMap<>();
        primaryVariable = null;
    }

    //static variables
    static Map<Character, Var> vars = new HashMap<>();
    static Var primaryVariable = null;

    //local variables
    char c;

    public Var(char c) {
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public Var(){}
}
