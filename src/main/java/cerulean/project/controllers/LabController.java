package cerulean.project.controllers;
import cerulean.project.services.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value ="/labs")
public class LabController {
    private LabService labService;

    private  Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void listLabs(@RequestParam String id, HttpServletResponse httpResponse) {
//        return gson.toJson(labService.getLabsCreatedByUser());
    }
    @RequestMapping(value ="/lab", method = RequestMethod.GET)
    public void getLab(@RequestParam String id) {
        ;
    }
    @RequestMapping(value ="/lab", method = RequestMethod.POST)
    public void addLab(@RequestBody String id) {
        ;
    }
}
