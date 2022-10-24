/* (C)2022 */
package calculator.security;

import calculator.UserRepository;
import calculator.model.User;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String ROLE_PREFIX = "ROLE_";

  @Autowired UserRepository userRepository;

  /**
   * loads a user from the DB by username
   *
   * @param username the username
   * @return the user
   * @throws UsernameNotFoundException if user not found
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findById(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("Username not found");
    }

    // set the username of the user
    org.springframework.security.core.userdetails.User.UserBuilder userBuilder =
        org.springframework.security.core.userdetails.User.withUsername(username);
    // set the password of the user
    userBuilder.password(user.get().getPassword());

    userBuilder.roles(user.get().getRole());

    // build the user
    return userBuilder.build();
  }
}
