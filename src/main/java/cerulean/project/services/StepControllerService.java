package cerulean.project.services;

import cerulean.project.database.LabRepository;
import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import cerulean.project.models.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StepControllerService {

    @Autowired
    private LabAssignmentService labAssignmentService;
    public boolean isStepValid(Step step, LabAssignment labAssignment) {
        Lab workingLab = labAssignmentService.getLabInAssignment(labAssignment);
        try {
            Step attemptedStep = workingLab.getSteps().get(step.getIndex());
            return step.stepMatches(attemptedStep);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
