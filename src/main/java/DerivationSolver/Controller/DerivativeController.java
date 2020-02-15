package DerivationSolver.Controller;

import DerivationSolver.DTO.Response;
import DerivationSolver.Service.DerivativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/derivative/")
public class DerivativeController {

    @Autowired
    private DerivativeService derivativeService;

    @GetMapping("")
    public Response generateDerivative(@RequestParam("expression") String expression, @RequestParam("points")List<Integer> points){
        return derivativeService.evaluate(expression, points);
    }
}
