package cerulean.project.controllers;
import cerulean.project.models.Account;
import cerulean.project.models.Lab;
import cerulean.project.models.LabAssignment;
import cerulean.project.models.Part;
import cerulean.project.services.AccountService;
import cerulean.project.services.LabAssignmentService;
import cerulean.project.services.LabService;
import cerulean.project.services.PartControllerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value ="/api/labs")
public class LabController {

    @Autowired
    private LabService labService;

    @Autowired
    PartControllerService partService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LabAssignmentService labAssignmentService;

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

    @RequestMapping(value ="/lab/updatelab", method = RequestMethod.POST)
    public void updateLab(@RequestBody String labJson, @RequestParam String username) {
        System.out.println(labJson);



        JsonObject jsonObject = gson.fromJson(labJson, JsonObject.class);
        JsonArray j_stepsarr = jsonObject.getAsJsonArray("steps");


        List<Part> partsList = new ArrayList<>();
        for(int i = 0 ; i < j_stepsarr.size(); i++){
            String partID = j_stepsarr.get(i).getAsJsonObject().remove("newPart").toString();
            Part p = partService.getPart(partID.substring(1,partID.length()-1));
            partsList.add(p);
            j_stepsarr.get(i).getAsJsonObject().remove("newPart");
        }

        Lab lab = gson.fromJson(jsonObject, Lab.class);

        for(int i = 0; i < lab.getSteps().size(); i++){
            lab.getSteps().get(i).setNewPart(partsList.get(i));
        }

        lab.setPartsList(partsList);
        labService.updateLab(lab);
        System.out.println("Done");

    }

    @RequestMapping(value ="/lab/{labId}", method = RequestMethod.GET)
    public String getPartsInLab(@PathVariable String labId) {
        Lab lab = labService.getLab(labId);
        return gson.toJson(lab.getPartsList());
    }

    @RequestMapping(value = "/deletelab", method = RequestMethod.POST)
    public ResponseEntity<String> deletelab(@RequestParam String labID) {

        try {
            labService.deleteLab(labID);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(labID);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/publishlab", method = RequestMethod.POST)
    public ResponseEntity<String> publishlab(@RequestParam String labId) {

        try {
            labService.publishLab(labId);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/copylab", method = RequestMethod.POST)
    public ResponseEntity<String> copylab(@RequestParam String labId,@RequestParam String username) {

        Lab lab = labService.getLab(labId);
        Account acc = accountService.getAccount(username);
        Lab newLab = lab.cloneLab();
        labService.addNewLab(username , newLab);
        acc.getCreatedLabs_ids().add(newLab.get_id());
        accountService.save(acc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getassignedlabs", method = RequestMethod.GET)
    public String getAssignedLabs(@RequestParam String username) {

        List<LabAssignment> labAssignments = labAssignmentService.getLabsAssignedToAccount(username);
        List<Lab> assigned_labs = new ArrayList<>();
        if(labAssignments != null){
            for (LabAssignment labAssignment : labAssignments) {
                assigned_labs.add(labService.getLab(labAssignment.getLabId()));
            }
            return gson.toJson(assigned_labs);
        }

        return null;
    }

    @RequestMapping(value = "/assignlab", method = RequestMethod.POST)
    public String assignLab(@RequestBody String assignBody) {
        System.out.println("Assign lab post request");

        JsonObject jsonObject = gson.fromJson(assignBody, JsonObject.class);
        String assignee_username = jsonObject.get("assignee").toString().replace("\"", "");
        String assigner_username = jsonObject.get("assigner").toString().replace("\"", "");
        String labId = jsonObject.get("labId").toString().replace("\"", "");

//        Account assigner = accountService.getAccount(assigner_username);
//        Account assignee = accountService.getAccount(assignee_username);
//        Lab lab = labService.getLab(labId);
        labAssignmentService.assignLab(assigner_username,assignee_username,labId);


        System.out.println("TEST");


        return "success";
    }


}
