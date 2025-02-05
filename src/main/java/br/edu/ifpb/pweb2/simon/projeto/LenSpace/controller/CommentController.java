package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.CommentService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;

    @PostMapping("saveComment")
    public ResponseEntity<Map<String, Object>> salvarComment(HttpSession session,
                                                             String comentario,
                                                             Long postId){
        User user = (User) session.getAttribute("usuarioLogado");
        Post post = postService.findByCodigoid(postId);

        if (user == null || post == null){
            return ResponseEntity.badRequest().body(Map.of("error", "Erro ao processar usuário."));
        }
        Comment novoComentario = commentService.saveComment(user, comentario, post);

        return ResponseEntity.ok(Map.of(
                "comentarioId", novoComentario.getCodigoid(),
                "username", user.username,
                "comentario", comentario
        ));
    }
}
