package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserById(Long id) {
        Optional<User> opUser = Optional.ofNullable(userRepository.findByCodigoid(id));
        return opUser.orElse(null);
    }

    public User findUserByEmail(String email) {
        Optional<User> opUser = Optional.ofNullable(userRepository.findByEmail(email));
        return opUser.orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllOtherUsers(Long id) {
        return userRepository.findAllOtherUsers(id);
    }

//    public User findUsernameByCodigoid(Long codigoid) {
//        return userRepository.findUsernameByCodigoid(codigoid);
//    }
}
