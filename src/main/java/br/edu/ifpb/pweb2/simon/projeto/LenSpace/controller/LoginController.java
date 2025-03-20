package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("usuarioLogado")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @ModelAttribute("usuarioLogado")
    public User getUsuarioLogado() {
        return new User();
    }

    @RequestMapping("/login")
    public String showLoginForm() {
        return "form-login";
    }

    @PostMapping("/auth/login")
    public void processLoginForm(String emailUsername, String senha, HttpSession session, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        User user = userService.findUserByEmailOrUsername(emailUsername);

        try {
            System.out.println(passwordEncoder.matches(senha, user.getPassword()));

            if (user == null) {
                redirectAttributes.addFlashAttribute("mensagemErro", "Usuário ou senha incorretos! Tente novamente.");
                response.sendRedirect("lenspace/login");
                return;
            }

            if(!passwordEncoder.matches(senha, user.getPassword())) {
                redirectAttributes.addFlashAttribute("mensagemErro", "Senha incorreta!");
                response.sendRedirect("lenspace/login");
                return;
            }


            if(!user.isEnabled()){
                redirectAttributes.addFlashAttribute("mensagemErro", "Usuário suspenso pela administração.");
                response.sendRedirect("lenspace/login");
                return;
            }

            session.setAttribute("usuarioLogado", user);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println("Usuário autenticado: " + user.getUsername());

            // ✅ Redireciona manualmente
            response.sendRedirect(request.getContextPath() + "/home");
        }catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro interno no servidor. Tente novamente.");
            try {
                response.sendRedirect("lenspace/login");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
