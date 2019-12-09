
package cerulean.project.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
public class ViewController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }
    @RequestMapping("/create")
    public String play() {
        return "create";
    }
    @RequestMapping("/part_builder")
    public String games() {
        return "part_builder";
    }
    @RequestMapping("/lab_builder")
    public String previousGames() {
        return "lab_builder";
    }
}