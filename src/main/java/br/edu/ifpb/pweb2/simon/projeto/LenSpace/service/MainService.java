package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
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

    public void settarPosts(List<Post> posts, HttpSession session, ModelAndView model){
        User user = (User) session.getAttribute("usuarioLogado");
        Map<Long, Long> likeCounts = new HashMap<>();
        Map<Long, List<Comment>> postComentarios = new HashMap<>();
        List<Long> postsCurtidos = postLikeService.getLikedPostsByUser(user);

        // Calcula o número de curtidas para cada post
        for (Post post : posts) {
            likeCounts.put(post.getCodigoid(), postLikeService.countLikes(post.getCodigoid()));
            postComentarios.put(post.getCodigoid(), postCommentService.findCommentByPostCodigoid(post.getCodigoid()));
        }

        model.addObject("postsList", posts);
        model.addObject("postComentarios", postComentarios);
        model.addObject("postsCurtidos", postsCurtidos);
        model.addObject("likeCounts", likeCounts);
    }

    public void settarPage(HttpSession session, ModelAndView model) {
        User user = (User) session.getAttribute("usuarioLogado");
        List<User> users = userService.findAllUsersNotFollowedByUserAndActive(user.getCodigoid());

        model.addObject("user", user);
        model.addObject("usersList", users);
    }
}
