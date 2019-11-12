package cerulean.project.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class PartController {

//    /parts							GET 		List Parts
//    /parts/part/{id}				    GET		    Get Part
//    /parts/part						POST		Add Part
//    Body:
//    {
//			*see part schema*
//    }


    @RequestMapping(value ="/", method = RequestMethod.GET)
    public void listParts(@RequestParam String id) {
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
