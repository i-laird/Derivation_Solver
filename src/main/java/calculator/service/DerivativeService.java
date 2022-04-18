package calculator.service;

import calculator.DTO.Response;
import calculator.util.Parser;
import calculator.terms.Term;
import calculator.util.StringToStream;
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
