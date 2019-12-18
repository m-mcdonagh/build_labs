package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabAssignmentRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Component
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
        Account account = accountService.getAccount(username);
        return findIncompleteLabAssignmentForAccount(account, labId);
    }

    public List<LabAssignment> getLabsAssignedToAccount(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccount(username);
        return getLabsAssignedToAccount(account);
    }

    public void assignLab(String assigner_username, String assignee_username, String labId) {
        // Check if lab is not already assigned.
        Account assigner = accountService.getAccount(assigner_username);
        Account assignee = accountService.getAccount(assignee_username);
        Lab labToAssign = labRepository.findById(labId).orElse(null);
        if (labToAssign != null) {
            assignLab(assigner, assignee, labToAssign);
        }
    }

    public List<String> getAccountIdsAssignedToLab(String labId) {
        // List<Account> results = new ArrayList<>();
        List<String> userIds = labAssignmentRepository.findAllByLabId(labId).stream()
                .map(LabAssignment::getUser_id)
                .collect(Collectors.toList());
        return userIds;
    }

    public List<LabAssignment> getLabAssignmentsForLab(String labId) {
        // List<Account> results = new ArrayList<>();
        List<LabAssignment> labAssignments = labAssignmentRepository.findAllByLabId(labId);
        return labAssignments;
    }



    private void assignLab(@NotNull Account assigner, @NotNull Account assignee, @NotNull Lab lab) {
        // Check if lab is not already assigned.
        LabAssignment currentAssignment = findIncompleteLabAssignmentForAccount(assignee, lab.get_id());
        if (currentAssignment != null) {
            throw new RuntimeException("Lab has already been assigned");
        }
        String id = UUID.randomUUID().toString();
        LabAssignment assignment = new LabAssignment(id,lab.get_id(), assigner.get_id(), assignee.get_id(),false, lab.getSteps().size(),lab.getName(),assignee.getUsername() );
        LabAssignment savedAssignment = labAssignmentRepository.save(assignment);
        assignee.getAssignedLabs_ids().add( Objects.requireNonNull(savedAssignment.get_id()) );
        accountRepository.save(assignee);
    }

    public void updateLabAssignment(@NotNull LabAssignment assignment) {
        labAssignmentRepository.save(assignment);
    }


    private List<LabAssignment> getLabsAssignedToAccount(Account account){
        List<LabAssignment> results = new ArrayList<>();
        List<String> assignmentIds = account.getAssignedLabs_ids();
        labAssignmentRepository.findAllById(assignmentIds).forEach(results::add);
        return results;
    }

    private LabAssignment findIncompleteLabAssignmentForAccount(Account account, String labId) throws UsernameNotFoundException {
        List<LabAssignment> assignmentsForAccount = getLabsAssignedToAccount(account);
        LabAssignment result = assignmentsForAccount.stream()
                .filter(assignment -> assignment.getLabId().equals(labId))
                .filter(assignment -> !assignment.getComplete())
                .findFirst()
                .orElse(null);
        return result;
    }
}
