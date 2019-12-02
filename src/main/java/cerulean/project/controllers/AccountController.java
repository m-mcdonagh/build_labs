package cerulean.project.controllers;
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
//@RequestMapping("/home")
//public class AccountController {
//    @Autowired
//    private AccountService account;
//
//
//}
