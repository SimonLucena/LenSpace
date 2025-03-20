package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.*;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostTagRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.TagRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
        List<Tag> tags = extrairTags(post);
    }

    public List<Post> findAllByUser(User user) {
        return postRepository.findAllByUser(user.getCodigoid());
    }

    public List<Post> findAllByActiveUser(User user) {
        return postRepository.findAllByUserAtivo(user.getCodigoid());
    }

    public Post findByCodigoid(Long postId) {
        return postRepository.findPostByCodigoid(postId);
    }

    public List<Tag> extrairTags(Post post) {
        String legenda = post.getLegenda();
        List<Tag> Tags = new ArrayList<>();

        if (legenda != null) {
            Pattern pattern = Pattern.compile("#\\w+"); // Captura palavras iniciadas com #
            Matcher matcher = pattern.matcher(legenda);

            while (matcher.find()) {
                String tagText = matcher.group().toLowerCase();
                Tag existingTag = tagRepository.findTagByTag(tagText);

                if (existingTag == null) {
                    existingTag = new Tag();
                    existingTag.setTag(tagText);
                    existingTag = tagRepository.save(existingTag);
                }

                Tags.add(existingTag);

                PostTag postTag = new PostTag();
                postTag.setPost(post);
                postTag.setTag(existingTag);
                postTag = postTagRepository.save(postTag);
            }
        }
        return Tags;
    }
}
