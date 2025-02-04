package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByCodigoid(Long codigoid);
    User findByEmail(String email);
    User findByUsername(String username);

    @Query("from User u where u.codigoid != :id")
    List<User> findAllOtherUsers(@Param("id") Long id);

    @Query("from User u where (u.email = :emailUsername or u.username = :emailUsername) and u.senha = :senha")
    User findUserByEmailOrUsernameAndSenha(String emailUsername, String senha);

    @Query("select u from User u where u.codigoid != :id and u.codigoid not in (select uf.follow.codigoid from UserFollow uf where uf.user.codigoid = :id)")
    List<User> findAllUsersNotFollowedBy(@Param("id") Long id);
}