package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<String> getCreatedLabIdsForUser(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findByUsername(username);
        if (acc == null)
            throw new UsernameNotFoundException("User not found");
        return acc.getCreatedLabs_ids();
    }


}
