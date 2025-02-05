package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.PostLike;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    @Query("from PostLike pl where pl.post.codigoid = :postId and pl.user.codigoid = :userId")
    Optional<PostLike> findByPostIdAndUser(Long postId, Long userId);

    @Query("select count(pl.codigoid) from PostLike pl where pl.post.codigoid = :postId")
    long countByPostCodigoid(Long postId);
}