package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserFollowRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserFollowService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FollowersController {
    @Autowired
    UserService userService;
    @Autowired
    UserFollowService userFollowService;

    @RequestMapping("/followers")
    public ModelAndView followersPage (ModelAndView model, HttpSession session){
        User user = (User) session.getAttribute("usuarioLogado");
        System.out.println(user);

        if(user != null){
            List<User> followers = userFollowService.findAllByUserId(user.getCodigoid());
            model.addObject("followerList", followers);
            System.out.println(followers);
            if (followers.isEmpty()) {
                model.addObject("erroMessage", "Nenhum seguidor encontrado");
            }
            model.setViewName("grid-followers");
        }else{
            model.setViewName("form-login");
        }

        return model;
    }
}
