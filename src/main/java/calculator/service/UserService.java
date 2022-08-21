package calculator.service;

import calculator.UserRepository;
import calculator.exception.UserNotFound;
import calculator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private void register(String email, String password){

        // see if the user already exists
        Optional<User> optionalUser = userRepository.findById(email);

        if(optionalUser.isEmpty()){
            final Random r = new SecureRandom();
            byte [] buffer = new byte[32];
            r.nextBytes(buffer);

            String salt = new String(buffer);

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setSalt(salt.toString());
            newUser.setHashedPassword(passwordEncoder.encode(salt + password));

            userRepository.save(newUser);
        }
    }

    private boolean login(String email, String password){
        User user = userRepository.findById(email).orElseThrow(UserNotFound::new);
        return passwordEncoder.matches(user.getSalt() + password, user.getHashedPassword());
    }

}
