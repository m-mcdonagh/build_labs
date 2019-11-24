package cerulean.project.controllers;


import cerulean.project.models.Account;
import cerulean.project.services.AccountService;
import cerulean.project.services.StepControllerService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

//    /steps/step/{lab_Id}/{index}		GET		    Get Instruction
//    /steps/step						POST		Add Instruction


@RestController
@RequestMapping(value ="/steps")
public class StepController {
    private StepControllerService stepService;
    private AccountService accountService;
    private Gson gson = new Gson();


    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void listParts(@RequestParam String lab_id, @RequestParam String index, HttpServletResponse httpResponse) {


    }


}
