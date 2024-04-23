package ScoreManager.M.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model model) {
//        if (userService.authenticate(id, password)) {
//            return "redirect:/";
//        } else {
//            model.addAttribute("error", "IDまたはパスワードが違います");
//            return "login";
//        }
//    }
}
