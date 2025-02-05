package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.UserFollow;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserFollowRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFollowService {
    @Autowired
    private UserFollowRepository userFollowRepository;

    public UserFollowService(UserFollowRepository userFollowRepository){
        this.userFollowRepository = userFollowRepository;
    }

    @Transactional
    public void saveUserFollow(UserFollow userFollow){
        userFollowRepository.save(userFollow);
    }
    @Transactional
    public void deleteFollowing(UserFollow userFollow){
        userFollowRepository.delete(userFollow);
    }

    public List<User> findAllByUserId(Long codigoid) {
        List<UserFollow> followers = userFollowRepository.findAllByCodigoid(codigoid);
        return followers.stream()
                        .map(UserFollow::getFollow)
                        .collect(Collectors.toList());
    }

    public UserFollow findFollowing(User user, User userFollowing) {
        return userFollowRepository.findUserFollowByUserAndFollow(user, userFollowing);
    }
}
