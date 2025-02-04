package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("usuarioLogado")
    public User getUsuarioLogado() {
        return new User();
    }

    @RequestMapping("/login")
    public String showLoginForm() {
        return "form-login";
    }

    @PostMapping("/processLogin")
    public ModelAndView processLoginForm(String emailUsername, String senha, HttpSession session) {
        ModelAndView model = new ModelAndView();
        User user = userService.findUserByEmailOrUsernameAndSenha(emailUsername, senha);

        if (user != null){
            session.setAttribute("usuarioLogado", user);
            model.setViewName("redirect:/");
        }else{
            model.setViewName("form-login");
            model.addObject("mensagemErro", "Usuário ou senha incorretos! Tente novamente.");
        }
        return model;
    }
}
