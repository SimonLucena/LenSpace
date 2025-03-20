package br.edu.ifpb.pweb2.simon.projeto.LenSpace.config;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.Post;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.PostRepository;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DatabaseInitializer {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PostRepository postRepository){
        return args -> {
            if (userRepository.count() == 0) {
                System.out.println("Populando banco de dados.");
                Date dataNascimentoConvertida;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // Admin
                User adminUser = new User();
                dataNascimentoConvertida = sdf.parse("2001-12-01");
                adminUser.setNome("Admin");
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@admin");
                adminUser.setSenha("123");
                adminUser.setAdministrador(true);
                adminUser.setDataNascimento(dataNascimentoConvertida);
                userRepository.save(adminUser);

                // User 1
                User user1 = new User();
                dataNascimentoConvertida = sdf.parse("1999-07-22");
                user1.setNome("User1");
                user1.setUsername("user1");
                user1.setEmail("user1@teste");
                user1.setSenha("123");
                user1.setDataNascimento(dataNascimentoConvertida);
                userRepository.save(user1);

                // User 2
                User user2 = new User();
                dataNascimentoConvertida = sdf.parse("1978-09-06");
                user2.setNome("User2");
                user2.setUsername("user2");
                user2.setEmail("user2@teste");
                user2.setSenha("123");
                user2.setDataNascimento(dataNascimentoConvertida);
                userRepository.save(user2);

                // User 3
                User user3 = new User();
                dataNascimentoConvertida = sdf.parse("2006-03-30");
                user3.setNome("User3");
                user3.setUsername("user3");
                user3.setEmail("user3@teste");
                user3.setSenha("123");
                user3.setDataNascimento(dataNascimentoConvertida);
                userRepository.save(user3);

                // Post para Admin
                Post postAdmin = new Post();
                postAdmin.setUser(adminUser);
                postAdmin.setLegenda("Teste de post Admin");
                postAdmin.setImagem("/uploads/IMG_9342.JPG");
                postRepository.save(postAdmin);

                // Post para User1
                Post postUser1 = new Post();
                postUser1.setUser(user1);
                postUser1.setLegenda("Teste de post User1");
                postUser1.setImagem("/uploads/IMG_9385.JPG");
                postRepository.save(postUser1);

                // Post para User2
                Post postUser2 = new Post();
                postUser2.setUser(user2);
                postUser2.setLegenda("Teste de post User2");
                postRepository.save(postUser2);

                // Post para User3
                Post postUser3 = new Post();
                postUser3.setUser(user3);
                postUser3.setLegenda("Teste de post User3");
                postRepository.save(postUser3);

                System.out.println("Cadastros iniciais realizados com sucesso");
            }else {
                System.out.println("Usuários  já cadastrados.");
            }
        };
    }
}