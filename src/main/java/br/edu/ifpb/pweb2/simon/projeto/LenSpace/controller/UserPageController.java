package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostCommentRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.MainService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/perfil")
public class UserPageController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    PostCommentRepository postCommentRepository;
    @Autowired
    MainService mainService;

    @GetMapping("/{username}")
    public ModelAndView viewProfile(@PathVariable String username, HttpSession session, ModelAndView model){
        User userLogado = (User) session.getAttribute("usuarioLogado");

        if (userLogado != null){
            User userSelected = userService.findUserByUsername(username);
            List<Post> posts = postService.findAllByUser(userSelected);
            if (posts == null) posts = new ArrayList<>();

            mainService.settarPage(session, model);
            model.addObject("userPerfil", userSelected);
            model.addObject("userPerfilProprio", false);
            model.setViewName("user-page");

            mainService.settarPosts(posts, session, model);

            return model;
        }else{
            model.setViewName("redirect:/login");
            return model;
        }
    }
}
