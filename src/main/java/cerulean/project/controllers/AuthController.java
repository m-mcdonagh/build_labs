package cerulean.project.controllers;

import cerulean.project.models.Account;
import cerulean.project.services.UserRoles;
import cerulean.project.services.MongoDBUserDetailsManager;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for account management API endpoints
 *
 * As of now /login is omitted because I'm using the default form login
 */
@RestController
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private MongoDBUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestParam String username, @RequestParam String password, @RequestParam String email,
            HttpServletResponse httpResponse) {

        /**
        UserDetails newUser = User.builder().passwordEncoder(passwordEncoder::encode).username(username)
                .password(password).authorities(new SimpleGrantedAuthority(UserRoles.ROLE_USER)).build();
        userDetailsManager.createUser(newUser);
         **/
        System.out.println(username+password+email);
        Account newAccount = new Account(username, passwordEncoder.encode(password), email);

        userDetailsManager.createUser(newAccount);
        try {
            httpResponse.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value="/sessiontest")
    public String sessionTest() {
        Account principalUser = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        String email = principalUser.getEmail();
        return String.format("Hello, %s, your email is %s\n", username, email);
    }

}
