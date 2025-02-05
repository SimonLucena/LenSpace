package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.PostComment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.CommentRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Transactional
    public Comment saveComment(User user, String comentario, Post post) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setComentario(comentario);
        commentRepository.save(comment);

        PostComment postComment = new PostComment();
        postComment.setComment(comment);
        postComment.setPost(post);
        postCommentRepository.save(postComment);

        return comment;
    }
}
