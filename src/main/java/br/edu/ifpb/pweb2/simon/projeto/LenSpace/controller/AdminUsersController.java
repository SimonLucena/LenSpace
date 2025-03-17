package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminUsersController {
    @Autowired
    private UserService userService;

    @RequestMapping("/adminUsers")
    public ModelAndView adminUsers(ModelAndView model, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user != null) {
            List<User> usersList = userService.findAllUsersNotFollowedByUserAndActive(user.getCodigoid());
            List<User> users = userService.findAllUsersNotAdmin();
            model.addObject("user", user);
            model.addObject("usersList", usersList);
            model.addObject("usersGen", users);
            model.setViewName("grid-admin-users");
        }else {
            model.setViewName("redirect:/login");
        }
        return model;
    }

    @PostMapping("/toggleStatus")
    public String toggleStatus(Long userId) {
        User user = userService.findUserById(userId);
        if (user != null) {
            System.out.println(user.isEnabled());
            if(user.isEnabled()){
                userService.desativarUser(user);
            }else{
                userService.reativarUser(user);
            }
        }
        return "redirect:/adminUsers";
    }
}
