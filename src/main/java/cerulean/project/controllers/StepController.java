package cerulean.project.controllers;


import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import cerulean.project.models.Step;
import cerulean.project.services.AccountService;
import cerulean.project.services.LabService;
import cerulean.project.services.StepControllerService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

//    /steps/step/{lab_Id}/{index}		GET		    Get Instruction
//    /steps/step						POST		Add Instruction


@RestController
@RequestMapping(value ="/steps")
public class StepController {
    private StepControllerService stepService;
    private AccountService accountService;
    private LabService labService;

    private Gson gson = new Gson();


    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void listParts(@RequestParam String lab_id, @RequestParam String index, HttpServletResponse httpResponse) {


    }



    @RequestMapping(value ="/step/{labId}/{index}", method = RequestMethod.GET)
    public String getStepInLab(@RequestParam String labId, String index) {

        Lab lab = labService.getLab(labId);
        Step currentStep = lab.getSteps().get(Integer.parseInt(index));
        return gson.toJson(currentStep);

    }



}
