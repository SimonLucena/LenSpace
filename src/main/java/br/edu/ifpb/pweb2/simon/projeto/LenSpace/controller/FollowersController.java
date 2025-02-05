package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.UserFollow;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserFollowRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserFollowService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

        if(user != null){
            setFollowerPageAtr(model, user);
            List<User> followers;
            model.setViewName("grid-followers");
        }else{
            model.setViewName("form-login");
        }

        return model;
    }

    private void setFollowerPageAtr(ModelAndView model, User user) {
        List<User> followers = userFollowService.findAllByUserId(user.getCodigoid());
        List<User> users = userService.findAllUsersNotFollowedByUser(user.getCodigoid());
        model.addObject("followerList", followers);
        model.addObject("user", user);
        model.addObject("usersList", users);
        if (followers.isEmpty()) {
            model.addObject("erroMessage", "Nenhum seguidor encontrado");
        }
    }

    @PostMapping("/unfollow")
    public ModelAndView unfollow (ModelAndView model, Long followId, HttpSession session){
        User user = (User) session.getAttribute("usuarioLogado");
        User userFollowing = userService.findUserById(followId);
        if(user != null){
            setFollowerPageAtr(model, user);

            UserFollow following = userFollowService.findFollowing(user, userFollowing);
            if(following != null){
                userFollowService.deleteFollowing(following);
            }
            model.setViewName("redirect:/followers");
        }else{
            model.setViewName("redirect:/login");
        }
        return model;
    }
}
