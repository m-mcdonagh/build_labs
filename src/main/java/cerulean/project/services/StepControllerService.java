package cerulean.project.services;

import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import cerulean.project.models.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.stereotype.Component;

@Component
public class StepControllerService {

    @Autowired
    private LabService labService;

    @Autowired
    private LabAssignmentService labAssignmentService;

    public boolean isStepValid(Step step, LabAssignment labAssignment) {
        Lab workingLab = labService.getLabInAssignment(labAssignment);
        try {
            Step attemptedStep = workingLab.getSteps().get(step.getIndex());
            return step.stepMatches(attemptedStep);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public Integer performStep(Step step, LabAssignment labAssignment) {
        Lab workingLab = labService.getLabInAssignment(labAssignment);
        if (!isStepValid(step, labAssignment)) {
            throw new RuntimeException("That step is not valid");
        }
        labAssignment.setCurrentStep( step.getIndex() );
        labAssignmentService.updateLabAssignment(labAssignment);
        return labAssignment.getCurrentStep();
    }

    public void addNewStep(String labId, Step step){
        Lab lab = labService.getLab(labId);
        addNewStep(lab,step);
    }

    private void addNewStep(Lab lab, Step step){
        if(lab != null){
            lab.getSteps().add(step);
            //TODO: Save new step to database
        }
        else{
            throw new RuntimeException("lab not found");
        }
    }

}
