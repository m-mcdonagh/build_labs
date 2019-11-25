package cerulean.project.controllers;

import cerulean.project.models.Account;
import cerulean.project.models.Part;
import cerulean.project.services.AccountService;
import cerulean.project.services.PartControllerService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public String listParts(@RequestParam String id, HttpServletResponse httpResponse) {
        String username = "temp";
        Account account = accountService.getAccount(username);
        return gson.toJson(partService.getPartsCreatedByUser(account));
    }
    @RequestMapping(value ="/part", method = RequestMethod.GET)
    public String getPart(@RequestParam String id) {
        String username = "temp";
        Part part = partService.getPart(id);
        return gson.toJson(part);
    }
    @RequestMapping(value ="/part", method = RequestMethod.POST)
    public void addPart(@RequestBody String partJson) {
        String username = "temp";
        Part part = gson.fromJson(partJson, Part.class);
        partService.addNewPart(username , part);
    }

    @RequestMapping(value ="/part", method = RequestMethod.GET)
    public String getAllParts() {
        List<Part> part_list = partService.getAllParts();
        return gson.toJson(part_list);
    }

}
