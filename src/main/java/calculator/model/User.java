package calculator.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    private String email;

    private String hashedPassword;

    private String salt;
}
