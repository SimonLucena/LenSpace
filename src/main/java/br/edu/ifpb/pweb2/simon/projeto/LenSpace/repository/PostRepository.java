package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.user.codigoid = :userId")
    List<Post> findAllByUser(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p WHERE p.user.codigoid = :userId OR p.user IN (SELECT uf.follow FROM UserFollow uf WHERE uf.user.codigoid = :userId and uf.user.ativo) order by p.dataPost desc")
    List<Post> findAllByUserAtivo(Long userId);

    Post findPostByCodigoid(Long postId);
}