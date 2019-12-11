package cerulean.project.controllers;


import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import cerulean.project.models.Step;
import cerulean.project.services.LabAssignmentService;
import cerulean.project.services.LabService;
import cerulean.project.services.StepControllerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

//    /steps/step/{lab_Id}/{index}		GET		    Get Instruction
//    /steps/step						POST		Add Instruction


@RestController
@RequestMapping(value ="/api/steps")
public class StepController {

    @Autowired
    private StepControllerService stepService;

    @Autowired
    private LabAssignmentService labAssignmentService;

    @Autowired
    private LabService labService;

    private Gson gson = new Gson();
    
    @RequestMapping(value ="/validate", method = RequestMethod.POST)
    public Boolean performStep(@RequestBody String stepJson, @RequestParam String labAssignmentId, HttpServletResponse httpResponse) {
        Step step = gson.fromJson(stepJson, Step.class);
        LabAssignment labAssignment = labAssignmentService.getLabAssignment(labAssignmentId);
        return stepService.isStepValid(step, labAssignment);
    }


    @RequestMapping(value ="/step/{labId}/{index}", method = RequestMethod.GET)
    public String getStepInLab(@RequestParam String labId, String index) {

        Lab lab = labService.getLab(labId);
        Step currentStep = lab.getSteps().get(Integer.parseInt(index));
        return gson.toJson(currentStep);

    }

}
