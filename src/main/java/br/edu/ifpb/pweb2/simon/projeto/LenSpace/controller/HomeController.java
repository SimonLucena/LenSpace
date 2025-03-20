package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.*;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private PostCommentService postCommentService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private MainService mainService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView model, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user != null){
            mainService.settarPage(session, model);

            List<Post> posts = postService.findAllByActiveUser(user);

            if (posts == null) posts = new ArrayList<>();


            model.setViewName("index");

            mainService.settarPosts(posts, session, model);
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

    @GetMapping("/hashtag/{tag}")
    public ModelAndView getPostsByHashtag(@PathVariable String tag, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView model = new ModelAndView("postsByHashtag");
        mainService.settarPage(session, model);

        // Busca a hashtag no banco
        Tag hashtag = tagService.findByTag(tag);

        if (hashtag != null) {
            List<Post> posts = tagService.findPostByTag(hashtag);
            model.addObject("posts", posts);
            model.addObject("hashtag", hashtag); // Garante que a hashtag é adicionada
            mainService.settarPosts(posts, session, model);
        } else {
            model.addObject("hashtag", new Tag()); // Se não existir, cria um objeto temporário
            model.addObject("mensagemErro", "Nenhum post encontrado com essa hashtag.");
        }

        return model;
    }

}