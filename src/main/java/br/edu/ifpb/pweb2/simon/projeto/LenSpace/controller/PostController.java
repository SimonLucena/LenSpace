package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostLikeService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostLikeService postLikeService;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @PostMapping("lancarPost")
    public ModelAndView post(String postLegenda, MultipartFile postImagem, HttpSession session, ModelAndView model) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            String fileName = postImagem.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, postImagem.getBytes());

            Post post = new Post();
            post.setUser(user);
            post.setLegenda(postLegenda);
            post.setImagem("/uploads/" + fileName);

            postService.savePost(post);
        }catch (IOException e){
            model.addObject("mensagemErro", "Erro ao fazer upload da imagem.");
        }
        model.setViewName("redirect:/");
        return model;
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

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(HttpSession session, MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            String fileUrl = "/uploads/" + fileName;
            response.put("fileUrl", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Erro ao tentar salvar a imagem para a postagem.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
