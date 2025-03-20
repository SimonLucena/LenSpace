package br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository  extends JpaRepository<Tag, Integer> {

    @Query("select t from Tag t where t.tag = :s")
    Tag findTagByTag(String s);

    public List<Tag> findAllByTag(String tag);

    @Query("select pt.post from PostTag pt  where pt.tag = :tag")
    List<Post> findAllPostsByTag(Tag tag);
}
