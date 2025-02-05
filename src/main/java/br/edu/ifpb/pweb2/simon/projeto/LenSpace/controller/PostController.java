package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostLikeService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostLikeService postLikeService;

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

        postService.savePost(post);

        return new ModelAndView("redirect:/");
    }

    @PostMapping("/like")
    public ResponseEntity<Map<String, Object>> likePost(HttpSession session, Long postId) {
        User user = (User) session.getAttribute("usuarioLogado");
        Map<String, Object> response = new HashMap<>();

        boolean liked = postLikeService.toggleLike(postId, user);
        response.put("liked", liked);
        response.put("likeCount", postLikeService.countLikes(postId));

        return ResponseEntity.ok(response);
    }

    public long likeCount(Long postId){
        return postLikeService.countLikes(postId);
    }
}
