package cerulean.project.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value="/axios")
public class AxiosController {


    private Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String getName() {
        System.out.println("GOT HERE");
        return gson.toJson("MY NAME IS KHURRAM");
    }



}
