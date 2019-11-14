package cerulean.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value ="/parts")
public class PartController {

//    /parts							GET 		List Parts
//    /parts/part/{id}				    GET		    Get Part
//    /parts/part						POST		Add Part
//    Body:
//    {
//			*see part schema*
//    }


    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void listParts(@RequestParam String id, HttpServletResponse httpResponse) {
        return;
    }
    @RequestMapping(value ="/part", method = RequestMethod.GET)
    public void getPart(@RequestParam String id) {
        ;
    }
    @RequestMapping(value ="/part", method = RequestMethod.POST)
    public void addPart(@RequestBody String id) {
        ;
    }

}
