package DerivationSolver.DTO;

public class Response {
    public String antiderivative;
    public Double result;

    public Response(String antiderivative, Double result) {
        this.antiderivative = antiderivative;
        this.result = result;
    }
}
