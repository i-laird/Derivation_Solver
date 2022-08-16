package calculator.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class User {

    @Id
    private UUID id;

    private String hashedPassword;

    private String salt;
}
