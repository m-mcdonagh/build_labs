package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * This class implements UserDetailsService, which is the interface responsible for loading UserDetails objects given
 * a username.
 * This specific implementation points to a mongoDB database
 *
 * Built on top of this tutorial:
 * https://medium.com/@gtommee97/rest-authentication-with-spring-security-and-mongodb-28c06da25fb1
 */
@Component
@Service
public class MongoDBUserDetailsManager implements UserDetailsManager {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (!userExists(username)) {
            throw new UsernameNotFoundException("User not found");
        }
        return account;

    }

    public Account loadAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public void persistAccount(Account account) {
        accountRepository.save(account);
    }


    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        Account newAccount = (Account) user; // Throws ClassCastException if user is not Account
        accountRepository.save(newAccount);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return accountRepository.findByUsername(username) != null;
    }

}