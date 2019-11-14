package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabAssignmentRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class LabAssignmentService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LabAssignmentRepository labAssignmentRepository;

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private AccountRepository accountRepository;

    public LabAssignment getLabAssignment(String id) {
        return labAssignmentRepository.findById(id).orElse(null);
    }

    public LabAssignment findIncompleteLabAssignmentForAccount(String username, String labId) throws UsernameNotFoundException {
        List<LabAssignment> assignmentsForAccount = getLabsAssignedToAccount(username);
        LabAssignment result = assignmentsForAccount.stream()
                .filter(assignment -> assignment.getLabId().equals(labId))
                .filter(assignment -> !assignment.getComplete())
                .findFirst()
                .orElse(null);
        return result;
    }

    public List<LabAssignment> getLabsAssignedToAccount(String username) throws UsernameNotFoundException {
        List<LabAssignment> results = new ArrayList<>();
        List<String> assignmentIds = accountService.getAccount(username).getAssignedLabs_ids();
        labAssignmentRepository.findAllById(assignmentIds).forEach(results::add);
        return results;
    }

    public List<String> getAccountIdsAssignedToLab(String labId) {
        // List<Account> results = new ArrayList<>();
        List<String> userIds = labAssignmentRepository.findAllByLabId(labId).stream()
                .map(LabAssignment::getUser_id)
                .collect(Collectors.toList());
        return userIds;
    }

    public void assignLab(@NotNull Account assigner, @NotNull Account assignee, @NotNull Lab lab) {
        LabAssignment assignment = new LabAssignment(
                lab.get_id(), assigner.get_id(), assignee.get_id(),false, lab.getSteps().size() );
        LabAssignment savedAssignment = labAssignmentRepository.save(assignment);
        assignee.getAssignedLabs_ids().add( Objects.requireNonNull(savedAssignment.get_id()) );
        accountRepository.save(assignee);
    }

    public void updateLabAssignment(@NotNull LabAssignment assignment) {
        labAssignmentRepository.save(assignment);
    }

}
