package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    @Query("select pc.comment from PostComment pc where pc.post.codigoid = :id")
    List<Comment> findCommentByPostCodigoid(Long id);

    @Transactional
    @Modifying
    @Query("delete from PostComment pc where pc.comment.codigoid = :commentId")
    void deleteByCommentId(Long commentId);
}