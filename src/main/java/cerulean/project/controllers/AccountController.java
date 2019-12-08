package cerulean.project.controllers;
import cerulean.project.models.Account;
import cerulean.project.services.AccountService;
import cerulean.project.services.MongoDBUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//          /assignments/share/{lab_id, username}	POST		Share lab w/ user
//        /accounts						            GET		Get Users
//        /accounts/account/{username}			    GET		Get Usergi
//        /accounts/account/register				POST		Register
//        /accounts/account/login				    POST		Login
//        /accounts/account/logout				    POST		Logout
//        /accounts/						        GET
//

//@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value ="/account", method = RequestMethod.GET)
    public String getAccount(@RequestParam String username) {
        return new Gson().toJson(accountService.getAccount(username));
    }


//    // /assignments/share/{lab_id, username}	POST		Share lab w/ user
//    @RequestMapping(value ="/share", method = RequestMethod.POST)
//    public void shareLab() {
//
//    }
//
//    // /accounts						            GET		Get Users
//    @RequestMapping(value ="/", method = RequestMethod.GET)
//    public void getAccounts() {
//
//    }
//
//    // /accounts/account/{username}			    GET		Get Usergi
//    @RequestMapping(value ="/account", method = RequestMethod.GET)
//    public void getUserInfo() {
//
//    }
//
//    // /accounts/account/register				POST		Register
//    @RequestMapping(value ="/account/register", method = RequestMethod.POST)
//    public void register() {
//
//    }
//
//    //        /accounts/account/login				    POST		Login
//    @RequestMapping(value ="/account/login", method = RequestMethod.POST)
//    public void login() {
//
//    }
//
//    //        /accounts/account/logout				    POST		Logout
//    @RequestMapping(value ="/account/logout", method = RequestMethod.POST)
//    public void logout() {
//
//    }




}
