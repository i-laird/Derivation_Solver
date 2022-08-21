package calculator.DTO;

import lombok.Data;
import lombok.NonNull;

@Data
public class ApiError {

    @NonNull
    public String message;
}
