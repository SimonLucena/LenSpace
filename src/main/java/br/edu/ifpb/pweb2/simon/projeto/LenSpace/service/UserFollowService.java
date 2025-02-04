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

    public List<User> findAllByUserId(Long codigoid) {
        System.out.println("Codigoid: "+codigoid);
        List<UserFollow> followers = userFollowRepository.findAllByCodigoid(codigoid);
        System.out.println("Seguidores: "+followers);
        return followers.stream()
                        .map(UserFollow::getFollow)
                        .collect(Collectors.toList());
    }

//    public List<User> findAllFollowByUser(User user){
//        return userFollowRepository.findAllUsersNotFollowedByUser(user.getCodigoid());
//    }
}
