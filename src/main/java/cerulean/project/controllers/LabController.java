package cerulean.project.controllers;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import cerulean.project.models.Part;
import cerulean.project.services.AccountService;
import cerulean.project.services.LabService;
import cerulean.project.services.PartControllerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value ="/labs")
public class LabController {

    @Autowired
    private LabService labService;

    @Autowired
    PartControllerService partService;

    @Autowired
    private AccountService accountService;

    private  Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String listLabs(@RequestParam String id, HttpServletResponse httpResponse) {
        String username = "temp";
        System.out.println("Lab get method");
        List<Lab> labs = labService.getLabsCreatedByUser(username);
        return gson.toJson(labs);
    }
    @RequestMapping(value ="/lab", method = RequestMethod.GET)
    public String getLab(@RequestParam String id) {
        String username = "temp";
        Lab lab = labService.getLab(id);
        return gson.toJson(lab);
    }
    @RequestMapping(value ="/lab", method = RequestMethod.POST)
    public void addLab(@RequestBody String labJson, @RequestParam String username) {
        System.out.println(labJson);

        String creator_id = accountService.getAccount(username).get_id();
        String id = UUID.randomUUID().toString();

        JsonObject jsonObject = gson.fromJson(labJson, JsonObject.class);
        JsonArray jarray = jsonObject.getAsJsonArray("partsList");
        List<String>partIDs = new ArrayList<>();
        for(int i = 0; i<jarray.size();i++){
            JsonObject obj = jarray.get(i).getAsJsonObject();
            partIDs.add(obj.remove("id").toString());
        }
        jsonObject.remove("partsList");

        jsonObject.addProperty("_id",id);
        jsonObject.addProperty("assignedTo_ids","[]");
        jsonObject.addProperty("labCreator_ids",creator_id);
        jsonObject.addProperty("labCreator_field",creator_id);
        Lab lab = gson.fromJson(jsonObject, Lab.class);

        List<Part>partsList = new ArrayList<>();
        partIDs.forEach((n) -> partsList.add(partService.getPart(n)));
        System.out.println("Done");
        //labService.addNewLab(username , lab);
    }

    @RequestMapping(value ="/lab/{labId}", method = RequestMethod.GET)
    public String getPartsInLab(@PathVariable String labId) {
        Lab lab = labService.getLab(labId);
        return gson.toJson(lab.getPartsList());
    }
}
