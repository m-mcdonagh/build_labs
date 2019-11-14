package cerulean.project.services;

import cerulean.project.models.LabAssignment;
import cerulean.project.models.Step;
import org.springframework.stereotype.Component;

@Component
public class StepControllerService {

    public boolean isStepValid(Step step, LabAssignment labAssignment) {
        return true;
    }
}
