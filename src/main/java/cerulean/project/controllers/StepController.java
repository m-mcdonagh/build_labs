package cerulean.project.controllers;


import cerulean.project.models.LabAssignment;
import cerulean.project.models.Step;
import cerulean.project.services.LabAssignmentService;
import cerulean.project.services.StepControllerService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

//    /steps/step/{lab_Id}/{index}		GET		    Get Instruction
//    /steps/step						POST		Add Instruction


@RestController
@RequestMapping(value ="/steps")
public class StepController {
    private StepControllerService stepService = new StepControllerService();
    private LabAssignmentService labAssignmentService = new LabAssignmentService();
    private Gson gson = new Gson();
    
    @RequestMapping(value ="/validate", method = RequestMethod.POST)
    public Boolean performStep(@RequestBody String stepJson, @RequestParam String labAssignmentId, HttpServletResponse httpResponse) {
        Step step = gson.fromJson(stepJson, Step.class);
        LabAssignment labAssignment = labAssignmentService.getLabAssignment(labAssignmentId);
        return stepService.isStepValid(step, labAssignment);
    }

}
