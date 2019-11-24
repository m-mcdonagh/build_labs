package cerulean.project.controllers;

import cerulean.project.models.Account;
import cerulean.project.models.Part;
import cerulean.project.services.AccountService;
import cerulean.project.services.PartControllerService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value ="/parts")
public class PartController {

//    /parts							GET 		List Parts
//    /parts/part/{id}				    GET		    Get Part
//    /parts/part						POST		Add Part
//    Body:
//    {
//			*see part schema*
//    }

    private PartControllerService partService;
    private AccountService accountService;

    private Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void listParts(@RequestParam String id, HttpServletResponse httpResponse) {

    }
    @RequestMapping(value ="/part", method = RequestMethod.GET)
    public void getPart(@RequestParam String id) {
        String username = "temp";
        return gson.toJson(partService.getPart(id));
    }
    @RequestMapping(value ="/part", method = RequestMethod.POST)
    public void addPart(@RequestBody String partJson) {
        String username = "temp";
        Part part = gson.fromJson(partJson, Part.class);
        partService.addNewPart(username , part);
    }

}
