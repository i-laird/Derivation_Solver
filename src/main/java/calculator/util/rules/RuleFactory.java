package calculator.util.rules;

import calculator.util.terms.Term;
import calculator.util.terms.Variable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class RuleFactory {

  private RuleFactory() {}

  public static QuotientRule makeFracRule(Term denominator, Term numerator) {
    return new QuotientRule(new LinkedList<>(Arrays.asList(denominator, numerator)));
  }

  public static ProductRule makeProductRule(Term left, Term right) {
    return new ProductRule(new LinkedList<>(Arrays.asList(left, right)));
  }

  public static ProductRule makeProductRule(LinkedList<Term> t) {
    return new ProductRule(t);
  }

  public static Term makeNaturalLogRule(Term inside) {
    Term toReturn = new NaturalLogRule(new LinkedList<>(Collections.singletonList(inside)));
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static Term makeLogRule(Term base, Term inside) {
    Term toReturn = new LogRule(new LinkedList<>(Arrays.asList(base, inside)));
    if (base.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static Term makePowerRule(Term inside, Term power) {
    Term toReturn = new PowerRule(new LinkedList<>(Arrays.asList(power, inside)));
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static Term makePowerFracRule(Term inside, Term topPow, Term bottomPow) {
    if (topPow.getClass() == Term.class && topPow.getNum() == 0) {
      return new Term(0);
    }
    return new PowerFracRule(new LinkedList<>(Arrays.asList(inside, topPow, bottomPow)));
  }

  public static ChainRule makeChainRule(Term outside, Term inside) {
    return new ChainRule(new LinkedList<>(Arrays.asList(outside, inside)));
  }

  public static AdditionRule makeAdditionRule(Term... t) {
    LinkedList<Term> ts = new LinkedList<Term>();
    Collections.addAll(ts, t);
    return new AdditionRule(ts);
  }

  public static AdditionRule makeAdditionRule(List<Term> t) {
    return new AdditionRule(new LinkedList<>(t));
  }

  public static SinRule createSinRule(Term inside) {
    return new SinRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeSinRule(Term inside) {
    Term toReturn = createSinRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static CosRule createCosRule(Term inside) {
    return new CosRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeCosRule(Term inside) {
    Term toReturn = createCosRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static TanRule createTanRule(Term inside) {
    return new TanRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeTanRule(Term inside) {
    Term toReturn = createTanRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static SecRule createSecRule(Term inside) {
    return new SecRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeSecRule(Term inside) {
    Term toReturn = createSecRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static CscRule createCscRule(Term inside) {
    return new CscRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeCscRule(Term inside) {
    Term toReturn = createCscRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static CotRule createCotRule(Term inside) {
    return new CotRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeCotRule(Term inside) {
    Term toReturn = createCotRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static SinhRule createSinhRule(Term inside) {
    return new SinhRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeSinhRule(Term inside) {
    Term toReturn = createSinhRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static CoshRule createCoshRule(Term inside) {
    return new CoshRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeCoshRule(Term inside) {
    Term toReturn = createCoshRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static TanhRule createTanhRule(Term inside) {
    return new TanhRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeTanhRule(Term inside) {
    Term toReturn = createTanhRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static SechRule createSechRule(Term inside) {
    return new SechRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeSechRule(Term inside) {
    Term toReturn = createSechRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static CschRule createCschRule(Term inside) {
    return new CschRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeCschRule(Term inside) {
    Term toReturn = createCschRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static CothRule createCothRule(Term inside) {
    return new CothRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeCothRule(Term inside) {
    Term toReturn = createCothRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static ArcSinRule createArcSinRule(Term inside) {
    return new ArcSinRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeArcSinRule(Term inside) {
    Term toReturn = createArcSinRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static ArcCosRule createArcCosRule(Term inside) {
    return new ArcCosRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeArcCosRule(Term inside) {
    Term toReturn = createArcCosRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static ArcTanRule createArcTanRule(Term inside) {
    return new ArcTanRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeArcTanRule(Term inside) {
    Term toReturn = createArcTanRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static ArcCotRule createArcCotRule(Term inside) {
    return new ArcCotRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeArcCotRule(Term inside) {
    Term toReturn = createArcCotRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static ArcSecRule createArcSecRule(Term inside) {
    return new ArcSecRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeArcSecRule(Term inside) {
    Term toReturn = createArcSecRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }

  public static ArcCscRule createArcCscRule(Term inside) {
    return new ArcCscRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static Term makeArcCscRule(Term inside) {
    Term toReturn = createArcCscRule(inside);
    if (inside.getClass() != Variable.class) {
      toReturn = makeChainRule(toReturn, inside);
    }
    return toReturn;
  }
}
