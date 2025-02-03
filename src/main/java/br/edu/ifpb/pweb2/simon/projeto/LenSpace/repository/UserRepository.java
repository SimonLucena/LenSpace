package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByCodigoid(Long codigoid);
    User findByEmail(String email);

    @Query("from User u where u.codigoid != :id")
    public List<User> findAllOtherUsers(@Param("id") Long id);

}