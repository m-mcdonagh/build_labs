package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findByUsername(username);
        if (acc == null)
            throw new UsernameNotFoundException("User not found");
        return acc;
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public String getSessionUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
