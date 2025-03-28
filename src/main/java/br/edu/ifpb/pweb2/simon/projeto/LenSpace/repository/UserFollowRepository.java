package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
    @Query("from UserFollow uf where uf.user.codigoid = :id")
    List<UserFollow> findAllByCodigoid(Long id);

    UserFollow findUserFollowByUserAndFollow(User user, User userFollowing);
}