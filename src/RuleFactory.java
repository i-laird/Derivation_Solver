import java.util.Arrays;
import java.util.LinkedList;

public class RuleFactory {
    public static final RuleFactory rf = new RuleFactory();
    private RuleFactory(){}
    public static RuleFactory getFactory(){return rf;}

    QuotientRule makeFracRule(Term top, Term bottom){
        return new QuotientRule(new LinkedList<>(Arrays.asList(top, bottom)));
    }

    ProductRule makeProductRule(Term left, Term right){
        return new ProductRule(new LinkedList<>(Arrays.asList(left, right)));
    }

    NaturalLogRule makeNaturalLogRule(Term inside){
        return new NaturalLogRule(new LinkedList<>(Arrays.asList(inside)));
    }

    PowerRule makePowerRule(Term inside, Term power){
        return new PowerRule(new LinkedList<>(Arrays.asList(inside, power)));
    }
}
