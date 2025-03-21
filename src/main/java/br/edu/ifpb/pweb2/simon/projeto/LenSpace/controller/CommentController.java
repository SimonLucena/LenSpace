package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.CommentService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostCommentService;
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
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    PostCommentService postCommentService;
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

    @PostMapping("editComment")
    public ResponseEntity<Map<String, Object>> editarComment(HttpSession session,
                                                             String comentario,
                                                             Long commentId){
        User user = (User) session.getAttribute("usuarioLogado");
        Comment comment = commentService.findCommentByCodigoid(commentId);

        if (user == null || comment == null){
            return ResponseEntity.badRequest().body(Map.of("error", "Erro ao processar usuário."));
        }

        System.out.println(comentario);
        comment.setComentario(comentario);
        System.out.println(comment.comentario);

        commentService.editComment(comment);

        return ResponseEntity.ok(Map.of(
                "comentarioId", comment.getCodigoid(),
//                "username", user.username,
                "comentario", comentario
        ));
    }

    @PostMapping("/deleteComment")
    public ResponseEntity<Map<String, Object>> deleteComment(Long commentId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 1️⃣ Excluir todos os PostComment relacionados ao comentário
            postCommentService.deleteByCommentId(commentId);

            // 2️⃣ Agora excluir o próprio comentário
            commentService.deleteComment(commentId);

            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Erro ao excluir comentário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
