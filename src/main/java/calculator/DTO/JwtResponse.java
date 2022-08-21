package calculator.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 65565656555L;
    private String jwttoken;
}

