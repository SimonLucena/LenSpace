package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("from Comment u where u.codigoid = :commentId")
    Comment findCommentByCodigoid(Long commentId);

    @Transactional
    void deleteCommentByCodigoid(Long commentId);

    @Transactional
    @Modifying
    @Query("update Comment u set u.comentario = :comment where u.codigoid = :commentId")
    void updadeCommentText(String comment, Long commentId);
}
