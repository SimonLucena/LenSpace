package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public void UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("login")
    public String showLoginForm() {
        return "form-login";
    }

    @PostMapping("processLogin")
    public String processLoginForm(Model model, String email, String senha, RedirectAttributes redirectAttributes) {
        String proxView;

        User user = userService.findUserByEmailAndSenha(email, senha);

        if (user != null){
            List<User> users = userService.findAllOtherUsers(user.getCodigoid());
            proxView = "index";
            model.addAttribute("user", user);
            model.addAttribute("usersList", users);
        }else{
            String mensagemErro = "Usuário ou senha incorretos! Tente novamente.";
            redirectAttributes.addFlashAttribute("mensagemErro", mensagemErro);
            proxView = "redirect:/login";
        }
        return proxView;
    }
}
