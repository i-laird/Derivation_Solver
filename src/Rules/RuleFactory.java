package Rules;

import Terms.Term;
import TrigFunctions.Hyperbolic.*;
import TrigFunctions.Trigenometric.*;

import java.util.Arrays;
import java.util.LinkedList;

public class RuleFactory {
    public static final RuleFactory rf = new RuleFactory();
    private RuleFactory(){}
    public static RuleFactory getFactory(){return rf;}

    public QuotientRule makeFracRule(Term top, Term bottom){
        return new QuotientRule(new LinkedList<>(Arrays.asList(top, bottom)));
    }

    public ProductRule makeProductRule(Term left, Term right){
        return new ProductRule(new LinkedList<>(Arrays.asList(left, right)));
    }

    public NaturalLogRule makeNaturalLogRule(Term inside){
        return new NaturalLogRule(new LinkedList<>(Arrays.asList(inside)));
    }

    public PowerRule makePowerRule(Term inside, Term power){
        return new PowerRule(new LinkedList<>(Arrays.asList(inside, power)));
    }

    public AdditionRule makeAdditionRule(Term ... t){
        LinkedList<Term> ts = new LinkedList<Term>();
        for (Term term : t){
            ts.add(term);
        }
        return new AdditionRule(ts);
    }

    public SinRule makeSinRule(Term inside){
        return new SinRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public CosRule makeCosRule(Term inside){
        return new CosRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public TanRule makeTanRule(Term inside){ return new TanRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public SecRule makeSecRule(Term inside){
        return new SecRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public CscRule makeCscRule(Term inside){
        return new CscRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public CotRule makeCotRule(Term inside){
        return new CotRule(new LinkedList<>(Arrays.asList(inside)));
    }

    public SinhRule makeSinhRule(Term inside){
        return new SinhRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public CoshRule makeCoshRule(Term inside){
        return new CoshRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public TanhRule makeTanhRule(Term inside){
        return new TanhRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public SechRule makeSechRule(Term inside){
        return new SechRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public CschRule makeCschRule(Term inside){
        return new CschRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public CothRule makeCothRule(Term inside){ return new CothRule(new LinkedList<>(Arrays.asList(inside))); }
}
