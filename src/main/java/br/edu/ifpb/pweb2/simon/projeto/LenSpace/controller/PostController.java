package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import com.itextpdf.layout.Document;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostCommentRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.CommentService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostCommentService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostLikeService;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.PostService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostLikeService postLikeService;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private CommentService commentService;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @PostMapping("lancarPost")
    public ModelAndView post(String postLegenda, @RequestParam(value = "postImagem", required = false) MultipartFile postImagem,
                             HttpSession session, ModelAndView model) {
        User user = (User) session.getAttribute("usuarioLogado");

        if (user == null) {
            return new ModelAndView("redirect:/login");
        }

        Post post = new Post();
        post.setUser(user);
        post.setLegenda(postLegenda);

        if (postImagem != null && !postImagem.isEmpty()) {
            try {
                String fileName = postImagem.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);

                Files.createDirectories(filePath.getParent());
                Files.write(filePath, postImagem.getBytes());

                post.setImagem("/uploads/" + fileName);
            } catch (IOException e) {
                model.addObject("mensagemErro", "Erro ao fazer upload da imagem.");
            }
        } else {
            post.setImagem(null);
        }

        postService.savePost(post);

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

    @GetMapping("export-comment/{postId}")
    void expCommentToPDF(@PathVariable Long postId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Comentarios.pdf");

        List<Comment> comments = postCommentService.findCommentByPostCodigoid(postId);
        System.out.println(postId);
        Post post = postService.findByCodigoid(postId);

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Comentários").setBold().setFontSize(18));

        Table table = new Table(new float[]{3, 7});
        table.useAllAvailableWidth();
        table.addHeaderCell(new Cell().add(new Paragraph("Usuário").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Comentário").setBold()));

        for (Comment comment : comments) {
            table.addCell(new Cell().add(new Paragraph(comment.getUser().username)));
            table.addCell(new Cell().add(new Paragraph(comment.comentario)));
        }

        document.add(table);
        document.close();
    }
}
