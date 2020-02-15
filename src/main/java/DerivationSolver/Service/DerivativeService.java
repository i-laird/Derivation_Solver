package DerivationSolver.Service;

import DerivationSolver.DTO.Response;
import DerivationSolver.Parser;
import DerivationSolver.Terms.Term;
import DerivationSolver.util.StringToStream;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DerivativeService {

    public Response evaluate(String expression, List<Integer> evalPoints){
        Parser parser = new Parser(StringToStream.convertStringToStream(expression));
        Term derivative = parser.getDeriv();
        return new Response(derivative.toString(), derivative.evaluate(evalPoints));
    }
}
