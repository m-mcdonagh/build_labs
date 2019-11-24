package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class LabService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private AccountService accountService;

    public List<Lab> getLabsCreatedByUser(String username) throws UsernameNotFoundException {
        List<Lab> results = new ArrayList<>();
        List<String> labIds = accountService.getAccount(username).getCreatedLabs_ids();
        labRepository.findAllById(labIds).forEach(results::add);
        return results;
    }

    public Lab getLab(String labId) {
        return labRepository.findById(labId).orElse(null);
    }

    public Lab getLabInAssignment(@NotNull LabAssignment assignment) {
        return labRepository.findById(assignment.getLabId()).orElse(null);
    }

    public void addNewLab(String username, Lab lab) {
    }
}
