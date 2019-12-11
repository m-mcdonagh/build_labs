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
@RequestMapping(value ="/api/labs")
public class LabController {

    @Autowired
    private LabService labService;

    @Autowired
    PartControllerService partService;

    @Autowired
    private AccountService accountService;

    private  Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String listLabs(HttpServletResponse httpResponse) {
        String username = "test2";
        System.out.println("Lab get method");
        List<Lab> labs = labService.getLabsCreatedByUser(username);
        return gson.toJson(labs);
    }
    @RequestMapping(value ="/lab", method = RequestMethod.GET)
    public String getLab(@RequestParam String id) {
        String username = "test2";
        System.out.print(id);
        Lab lab = labService.getLab(id);
        return gson.toJson(lab);
    }
    @RequestMapping(value ="/lab", method = RequestMethod.POST)
    public void addLab(@RequestBody String labJson, @RequestParam String username) {
        System.out.println(labJson);

        Account acc=  accountService.getAccount(username);
        String creator_id = acc.get_id();
        String id = UUID.randomUUID().toString();

        JsonObject jsonObject = gson.fromJson(labJson, JsonObject.class);
        JsonArray j_stepsarr = jsonObject.getAsJsonArray("steps");


        List<Part> partsList = new ArrayList<>();
        for(int i = 0 ; i < j_stepsarr.size(); i++){
            String partID = j_stepsarr.get(i).getAsJsonObject().remove("newPart").toString();
            Part p = partService.getPart(partID.substring(1,partID.length()-1));
            partsList.add(p);
            j_stepsarr.get(i).getAsJsonObject().remove("newPart");
        }

        jsonObject.addProperty("_id",id);
        //jsonObject.addProperty("assignedTo_Ids","[]"); Null
        jsonObject.addProperty("labCreator_Id",creator_id);
        Lab lab = gson.fromJson(jsonObject, Lab.class);

        for(int i = 0; i < lab.getSteps().size(); i++){
            lab.getSteps().get(i).setNewPart(partsList.get(i));
        }

        lab.setPartsList(partsList);

        System.out.println("Done");
        labService.addNewLab(username , lab);
        acc.getCreatedLabs_ids().add(id);
        accountService.save(acc);


        //j_stepsarr.forEach((step) -> partIds.add(step.getAsJsonObject().remove("newPart").toString()));


//

//        List<Part>partsList = new ArrayList<>();
//        for(int i = 0; i<jarray.size();i++){
//            String partID = jarray.get(i).toString();
//            System.out.println(partID);
//            partsList.add(partService.getPart(partID));
//
//        }


//        jsonObject.remove("partsList");

      //  Lab lab = gson.fromJson(jsonObject, Lab.class);


        //partIDs.forEach((n) -> partsList.add(partService.getPart(n)));
    }

    @RequestMapping(value ="/lab/{labId}", method = RequestMethod.GET)
    public String getPartsInLab(@PathVariable String labId) {
        Lab lab = labService.getLab(labId);
        return gson.toJson(lab.getPartsList());
    }
}
