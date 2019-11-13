package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class LabService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private AccountRepository accountRepository;

}
