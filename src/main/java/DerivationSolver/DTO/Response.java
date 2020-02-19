package DerivationSolver.DTO;

/**
 * Response DTO
 * @author Ian Laird
 */
public class Response {

    // the calculated antiderivative
    public String antiderivative;

    // the antiderivative value when evaluated
    public Double result;

    public Response(String antiderivative, Double result) {
        this.antiderivative = antiderivative;
        this.result = result;
    }
}
