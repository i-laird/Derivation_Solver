package calculator.service;

import calculator.UserRepository;
import calculator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User register(String email, String password){

        // see if the user already exists
        Optional<User> optionalUser = userRepository.findById(email);

        if(optionalUser.isPresent()){
            throw new RuntimeException("User Exists");
        }

        User newUser = new User();
        newUser.setUsername(email);
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }

}
