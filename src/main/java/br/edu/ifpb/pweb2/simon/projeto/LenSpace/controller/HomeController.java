package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    public String showHomePage() {
        return "index";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        String proxView;
        User user = (User) session.getAttribute("usuarioLogado");

        if (user != null){
            List<User> users = userService.findAllOtherUsers(user.getCodigoid());
            proxView = "index";
            model.addAttribute("user", user);
            model.addAttribute("usersList", users);
        }else{
            proxView = "redirect:/login";
        }
        return proxView;
    }
}