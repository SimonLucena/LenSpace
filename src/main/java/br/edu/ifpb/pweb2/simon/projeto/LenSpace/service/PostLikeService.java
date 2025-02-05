package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.PostLike;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostLikeRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostLikeService {
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostRepository postRepository;

    public boolean toggleLike(Long postId, User user) {
        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndUser(postId, user.getCodigoid());
        Post post = postRepository.findPostByCodigoid(postId);

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get()); // Descurtir
            return false;
        } else {
            PostLike newLike = new PostLike();
            newLike.setPost(post);
            newLike.setUser(user);
            postLikeRepository.save(newLike); // Curtir
            return true;
        }
    }

    public long countLikes(Long postId) {
        return postLikeRepository.countByPostCodigoid(postId);
    }

    public List<Long> getLikedPostsByUser(User user) {
        return postLikeRepository.findLikedPostsByUserCodigoid(user.getCodigoid());
    }
}
