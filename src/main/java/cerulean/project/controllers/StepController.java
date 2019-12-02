package cerulean.project.controllers;


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
    private Gson gson = new Gson();




    @RequestMapping(value ="/validate", method = RequestMethod.POST)
    public String performStep(@RequestBody String labAssignment_id, @RequestBody String step, HttpServletResponse httpResponse) {
        return "performing step";
    }


}
