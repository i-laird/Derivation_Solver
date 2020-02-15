package DerivationSolver;

import DerivationSolver.Terms.Term;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author Ian laird
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
        //Parser p = new Parser(System.in);
        //Term parsedStatement = p.getRoot();
    }
}
