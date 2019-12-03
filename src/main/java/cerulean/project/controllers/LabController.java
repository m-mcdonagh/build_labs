package cerulean.project.controllers;
import cerulean.project.models.Lab;
import cerulean.project.services.LabService;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(value ="/labs")
public class LabController {
    private LabService labService;

    private  Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String listLabs(@RequestParam String id, HttpServletResponse httpResponse) {
        String username = "temp";
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
    public void addLab(@RequestBody String labJson) {
        String username = "temp";
        Lab lab = gson.fromJson(labJson, Lab.class);
        labService.addNewLab(username , lab);
    }

    @RequestMapping(value ="/lab/{labId}", method = RequestMethod.GET)
    public String getPartsInLab(@PathVariable String labId) {
        Lab lab = labService.getLab(labId);
        return gson.toJson(lab.getPartsList());
    }
}
