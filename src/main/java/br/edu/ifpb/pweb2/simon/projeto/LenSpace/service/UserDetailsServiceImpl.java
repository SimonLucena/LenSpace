package br.edu.ifpb.pweb2.simon.projeto.LenSpace.service;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailUsername) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findUserByEmailOrUsername(emailUsername));

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        return optionalUser.get(); // O User já implementa UserDetails
    }
}
