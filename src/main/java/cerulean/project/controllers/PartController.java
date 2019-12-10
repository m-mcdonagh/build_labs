package cerulean.project.controllers;

import cerulean.project.models.Account;
import cerulean.project.models.Part;
import cerulean.project.services.AccountService;
import cerulean.project.services.PartControllerService;
import com.google.gson.*;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.util.BsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.security.MessageDigest;
@CrossOrigin(origins = "http://localhost:8081")
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


    @Autowired
    private PartControllerService partService;

    @Autowired
    private AccountService accountService;

    private Gson gson = new Gson();

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String listParts(@RequestParam String username, HttpServletResponse httpResponse) {
        username = "temp";
        Account account = accountService.getAccount(username);
        return gson.toJson(partService.getPartsCreatedByUser(account));
    }
    @RequestMapping(value ="/part", method = RequestMethod.GET)
    public String getPart(@RequestParam String id) {
        String username = "test2";

        Part part = partService.getPart(id);
        return gson.toJson(part);
    }

    @RequestMapping(value ="/part/{partID}", method = RequestMethod.GET)
    public String getPartById(@PathVariable String partID) {
        System.out.println("Edit part post get request");
        Part part = partService.getPart(partID);
        return gson.toJson(part);
    }

    @RequestMapping(value ="/part", method = RequestMethod.POST)
    public String addPart(@RequestBody String partJson, @RequestParam String username) {
        System.out.println("Add part controller");
        System.out.println("Add Part input : "+partJson+ " with username "+ username);

        JsonObject jsonObject = gson.fromJson(partJson, JsonObject.class);

       // Set<String> keys = jsonObject.keySet();

//        for(String s : keys){
//            System.out.println(s);
//        }

        List<List<Double>> slotPoints = new ArrayList<>();
        List<Double> connectorPoint = new ArrayList<>();

        JsonArray jarray = jsonObject.getAsJsonArray("slotPoints");
        String connectorPoint_String = jsonObject.get("connectorPoint").toString();
        jsonObject.remove("connectorPoint");

        String[] xy_s = connectorPoint_String.split(",");

        Double conn_x = Double.parseDouble(xy_s[0].substring(xy_s[0].indexOf(":")+1)) / 100;
        Double conn_y = Double.parseDouble(xy_s[1].substring(xy_s[1].indexOf(":")+1,xy_s[1].indexOf("}"))) / 100;

        connectorPoint.addAll(Arrays.asList(conn_x,conn_y));

        for(int i =0; i<jarray.size();i++){
            JsonObject obj = jsonObject.getAsJsonArray("slotPoints").get(i).getAsJsonObject();
            //obj.remove("id");
              Double x = Double.parseDouble(obj.remove("x").toString()) /100;
              Double y = Double.parseDouble(obj.remove("y").toString()) /100;
            //String  x = obj.remove("x").toString();
            //String  y = obj.remove("y").toString();
            slotPoints.add(Arrays.asList(x,y));
        }


        jsonObject.remove("slotPoints");
        String id = UUID.randomUUID().toString();
        jsonObject.addProperty("_id",id);
        jsonObject.addProperty("ispublished",false);

        //System.out.println(jsonObject);

        Part part = gson.fromJson(jsonObject, Part.class);
        part.setSlotPoints(slotPoints);
        part.setConnectorPoint(connectorPoint);



        return partService.addNewPart(username , part);
        //return "temp";

    }

    @RequestMapping(value ="/allparts", method = RequestMethod.GET)
    public String getAllParts() {
        return gson.toJson(partService.getAllParts());
    }


}




