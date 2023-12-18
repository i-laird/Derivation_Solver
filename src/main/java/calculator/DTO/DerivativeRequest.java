package calculator.DTO;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerivativeRequest implements Serializable {
  private static final long serialVersionUID = 6778676666L;
  public String expression;
  public List<Integer> points;
}
