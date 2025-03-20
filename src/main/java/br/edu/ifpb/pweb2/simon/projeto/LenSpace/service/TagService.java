package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Tag;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Tag findByTag(String tag){
        return tagRepository.findTagByTag("#"+tag.toLowerCase());
    }

    public List<Post> findPostByTag(Tag tag){
        return tagRepository.findAllPostsByTag(tag);
    }

    public List<Tag> findAllTag() {
        return tagRepository.findAll();
    }
}
