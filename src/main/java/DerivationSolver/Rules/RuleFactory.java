package DerivationSolver.Rules;

import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.Hyperbolic.*;
import DerivationSolver.TrigFunctions.InverseTrigenometic.ArcCosRule;
import DerivationSolver.TrigFunctions.InverseTrigenometic.ArcCotRule;
import DerivationSolver.TrigFunctions.InverseTrigenometic.ArcSinRule;
import DerivationSolver.TrigFunctions.InverseTrigenometic.ArcTanRule;
import DerivationSolver.TrigFunctions.Trigenometric.*;

import java.util.Arrays;
import java.util.LinkedList;

public class RuleFactory {
    public static final RuleFactory rf = new RuleFactory();
    private RuleFactory(){}
    public static RuleFactory getFactory(){return rf;}

    public QuotientRule makeFracRule(Term denominator, Term numerator){
        return new QuotientRule(new LinkedList<>(Arrays.asList(denominator, numerator)));
    }

    public ProductRule makeProductRule(Term left, Term right){
        return new ProductRule(new LinkedList<>(Arrays.asList(left, right)));
    }

    public NaturalLogRule makeNaturalLogRule(Term inside){
        return new NaturalLogRule(new LinkedList<>(Arrays.asList(inside)));
    }

    public LogRule makeLogRule(Term base, Term inside){
        return new LogRule(new LinkedList<>(Arrays.asList(base, inside)));
    }

    public PowerRule makePowerRule(Term inside, Term power){
        return new PowerRule(new LinkedList<>(Arrays.asList(inside, power)));
    }

    public Term makePowerFracRule(Term inside, Term topPow, Term bottomPow){
        if (topPow.getClass() == Term.class && topPow.getNum() == 0){
            return new Term(0);
        }
        return new PowerFracRule(new LinkedList<>(Arrays.asList(inside, topPow, bottomPow)));
    }

    public ChainRule makeChainRule(Term outside, Term inside){
        return new ChainRule(new LinkedList<>(Arrays.asList(outside, inside)));
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

    public ArcSinRule makeArcSinRule(Term inside){
        return new ArcSinRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public ArcCosRule makeArcCosRule(Term inside){
        return new ArcCosRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public ArcTanRule makeArcTanRule(Term inside){ return new ArcTanRule(new LinkedList<>(Arrays.asList(inside)));
    }
    public ArcCotRule makeArcCotRule(Term inside){
        return new ArcCotRule(new LinkedList<>(Arrays.asList(inside)));
    }
}
