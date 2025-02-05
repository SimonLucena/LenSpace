package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.UserFollow;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostLikeService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserFollowService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    public String showHomePage() {
        return "index";
    }

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostLikeService postLikeService;
    @Autowired
    private UserFollowService userFollowService;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView model, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user != null){
//            List<User> users = userService.findAllOtherUsers(user.getCodigoid());
            List<User> users = userService.findAllUsersNotFollowedByUserAndActive(user.getCodigoid());
            List<Post> posts = postService.findAllByActiveUser(user);

            if (users == null) users = new ArrayList<>();
            if (posts == null) posts = new ArrayList<>();

            Map<Long, Long> likeCounts = new HashMap<>();

            // Calcula o número de curtidas para cada post
            for (Post post : posts) {
                likeCounts.put(post.getCodigoid(), postLikeService.countLikes(post.getCodigoid()));
            }

            model.setViewName("index");
            model.addObject("user", user);
            model.addObject("usersList", users);
            model.addObject("postsList", posts);
            model.addObject("likeCounts", likeCounts);
        }else{
            model.setViewName("redirect:/login");
        }
        return model;
    }

    @RequestMapping("/logout")
    public ModelAndView logoutUser(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/follow")
    public String followUser (@RequestParam("userFollowId") Long userFollowId, HttpSession session, HttpServletRequest request){
        User userToFollow = userService.findUserById(userFollowId);
        User user = (User) session.getAttribute("usuarioLogado");

        if(userToFollow != null){
            UserFollow userFollow = new UserFollow();
            userFollow.setUser(user);
            userFollow.setFollow(userToFollow);

            userFollowService.saveUserFollow(userFollow);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}