package cerulean.project.services;

import cerulean.project.database.AccountRepository;
import cerulean.project.database.LabRepository;
import cerulean.project.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;

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
        labRepository.save(lab);
    }


    public List<Integer> validateLabLegality(Lab lab) {
        List<Integer> invalidSteps = new ArrayList<>();
        Map<Step, Set<Integer>> occupiedSlots = new HashMap<>();
        for (Step step : lab.getSteps()) {

            occupiedSlots.put(step, new HashSet<>()); // New step proposed
            Integer parentIndex = step.getParentIndex();

            // If this is the first step, there is nowhere for you to misplace something
            if (step.getId() == 0) {
                continue;
            }
            // If this step depends on an invalid parent or a nonexistent parent it's bad
            if (invalidSteps.contains(parentIndex) || parentIndex >= lab.getSteps().size()) {
                invalidSteps.add(step.getId());
                continue;
            }


            Step parentStep = lab.getSteps().get(parentIndex);
            Part parentPart = parentStep.getNewPart();

            boolean stepOutOfBounds = step.getParentSlot() >= parentPart.getSlotPoints().size();
            boolean slotAlreadyInUse = false;

            if (occupiedSlots.containsKey(parentStep)) {
                slotAlreadyInUse = occupiedSlots.get(parentStep).contains(step.getParentSlot());
            }
            boolean stepIsInvalid = stepOutOfBounds || slotAlreadyInUse;

            if (stepIsInvalid) {
                invalidSteps.add(step.getId());
            } else {
                occupiedSlots.get(parentStep).add(step.getParentSlot());
            }
        }
        return invalidSteps;
    }
}
