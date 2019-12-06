package cerulean.project.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value="/axios")
public class AxiosController {


    private Gson gson = new Gson();

    @RequestMapping(value ="/axios", method = RequestMethod.GET)
    public String getName() {
        System.out.println("GOT HERE");
        return gson.toJson("HELLO WORLD!");
    }

    @PostMapping(value = "/postPost")
    public void testPost(@RequestBody String input){
        System.out.println("POST EXECUTED");
        System.out.println("DATA SENT"+input);
    }



}
