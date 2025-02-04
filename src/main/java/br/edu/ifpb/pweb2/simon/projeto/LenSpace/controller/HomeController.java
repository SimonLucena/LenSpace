package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    public String showHomePage() {
        return "index";
    }

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView model, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user != null){
            List<User> users = userService.findAllOtherUsers(user.getCodigoid());
            List<Post> posts = postService.findAllByUser(user);

            if (users == null) users = new ArrayList<>();
            if (posts == null) posts = new ArrayList<>();

            model.setViewName("index");
            model.addObject("user", user);
            model.addObject("usersList", users);
            model.addObject("postsList", posts);
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

    @PostMapping("/post")
    public ModelAndView post(String legenda, String imagem, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user == null) {
            return new ModelAndView("redirect:/login");
        }

        Post post = new Post();
        post.setUser(user);
        post.setLegenda(legenda);
        post.setImagem(imagem);

        postService.save(post);

        return new ModelAndView("redirect:/");
    }
}