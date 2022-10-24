package calculator.util.rules;

import calculator.util.terms.Term;
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

  public static NaturalLogRule makeNaturalLogRule(Term inside) {
    return new NaturalLogRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static LogRule makeLogRule(Term base, Term inside) {
    return new LogRule(new LinkedList<>(Arrays.asList(base, inside)));
  }

  public static PowerRule makePowerRule(Term inside, Term power) {
    return new PowerRule(new LinkedList<>(Arrays.asList(inside, power)));
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
    LinkedList<Term> ts = new LinkedList<Term>();
    for (Term term : t) {
      ts.add(term);
    }
    return new AdditionRule(ts);
  }

  public static SinRule makeSinRule(Term inside) {
    return new SinRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static CosRule makeCosRule(Term inside) {
    return new CosRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static TanRule makeTanRule(Term inside) {
    return new TanRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static SecRule makeSecRule(Term inside) {
    return new SecRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static CscRule makeCscRule(Term inside) {
    return new CscRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static CotRule makeCotRule(Term inside) {
    return new CotRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static SinhRule makeSinhRule(Term inside) {
    return new SinhRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static CoshRule makeCoshRule(Term inside) {
    return new CoshRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static TanhRule makeTanhRule(Term inside) {
    return new TanhRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static SechRule makeSechRule(Term inside) {
    return new SechRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static CschRule makeCschRule(Term inside) {
    return new CschRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static CothRule makeCothRule(Term inside) {
    return new CothRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static ArcSinRule makeArcSinRule(Term inside) {
    return new ArcSinRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static ArcCosRule makeArcCosRule(Term inside) {
    return new ArcCosRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static ArcTanRule makeArcTanRule(Term inside) {
    return new ArcTanRule(new LinkedList<>(Collections.singletonList(inside)));
  }

  public static ArcCotRule makeArcCotRule(Term inside) {
    return new ArcCotRule(new LinkedList<>(Collections.singletonList(inside)));
  }
}
