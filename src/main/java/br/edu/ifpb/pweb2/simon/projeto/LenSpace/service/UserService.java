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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public User findUserByUsername(String username) {
        Optional<User> opUser = Optional.ofNullable(userRepository.findByUsername(username));
        return opUser.orElse(null);
    }

    public User findUserByEmailOrUsernameAndSenha(String email, String senha){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByEmailOrUsernameAndSenha(email, senha));
        return userOptional.orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllOtherUsers(Long id) {
        return userRepository.findAllOtherUsers(id);
    }


    public List<User> findAllUsersNotFollowedByUser(Long id) {
        return userRepository.findAllUsersNotFollowedBy(id);
    }

    public void desativarUser(User user) {
        userRepository.desativarUserByCodigoid(user.getCodigoid());
    }

    public void reativarUser(User user) {
        userRepository.reativarUserByCodigoid(user.getCodigoid());
    }

    public List<User> findAllUsersNotAdmin() {
        return userRepository.findAllUsersNotAdmin();
    }

    public List<User> findAllUsersNotFollowedByUserAndActive(Long codigoid) {
        return userRepository.findAllUsersNotFollowedByUserAndActive(codigoid);
    }

    public User findUserByEmailOrUsername(String emailUsername) {
        return userRepository.findUserByEmailOrUsername(emailUsername);
    }
}
