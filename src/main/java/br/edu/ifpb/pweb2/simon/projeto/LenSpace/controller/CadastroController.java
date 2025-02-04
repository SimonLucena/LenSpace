package br.edu.ifpb.pweb2.simon.projeto.LenSpace.controller;

import br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity.User;
import br.edu.ifpb.pweb2.simon.projeto.LenSpace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

@Controller
public class CadastroController {
    private UserService userService;

    public static boolean isSenhaValida(String senha) {
        String REGEX_SENHA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$";
        return senha != null && Pattern.matches(REGEX_SENHA, senha);
    }


    public static Boolean validarDataNascimento(String dataNascimentoStr) {
        int IDADE_MINIMA = 18;
        try {
            // Converte a string para LocalDate (Formato esperado: YYYY-MM-DD)
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate hoje = LocalDate.now();

            // Calcula a idade do usuário
            int idade = Period.between(dataNascimento, hoje).getYears();

            // Verifica se a idade é válida
            return (idade >= IDADE_MINIMA);
        } catch (Exception e) {
            return false;
        }
    }


    @Autowired
    public void UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("cadastro")
    public String showLoginForm() {
        return "form-cadastro";
    }

    @PostMapping("processCadastro")
    public String processCadastroForm(String nome, String username, String email, String senha, String dataNascimento, RedirectAttributes redirectAttributes) throws ParseException {
        // Verifica se todos os campos foram preenchidos
        if (nome == null || username == null || email == null || senha == null || dataNascimento == null) {
            return adicionarMensagemErro(redirectAttributes, "Preencha todos os campos.", "mensagemErro");
        }

        // Verifica se o email já está cadastrado
        if (userService.findUserByEmail(email) != null) {
            return adicionarMensagemErro(redirectAttributes, "Email já cadastrado.", "mensagemErroEmail");
        }

        // Verifica se o username já está sendo usado
        if (userService.findUserByUsername(username) != null) {
            return adicionarMensagemErro(redirectAttributes, "Username já utilizado.", "mensagemErroUsername");
        }

        // Verifica se a senha é válida
        if (!isSenhaValida(senha)) {
            return adicionarMensagemErro(redirectAttributes, "Senha inválida! Deve conter pelo menos 9 caracteres, uma letra maiúscula, uma letra minúscula e um número.", "mensagemErroSenha");
        }

        // Verifica se a data de nascimento é válida (idade mínima de 18 anos)
        if (!validarDataNascimento(dataNascimento)) {
            return adicionarMensagemErro(redirectAttributes, "Não é permitido o cadastro de usuários menores de 18 anos.", "mensagemErroData");
        }

        // Cadastro válido, redireciona para index
        return saveCadastroLogin(nome, username, email, senha, dataNascimento);
    }

    private String adicionarMensagemErro(RedirectAttributes redirectAttributes, String mensagem, String atributo) {
        redirectAttributes.addFlashAttribute(atributo, mensagem);
        return "redirect:/cadastro";
    }

    public String saveCadastroLogin(String nome, String username, String email, String senha, String dataNascimento) throws ParseException {
        Date dataNascimentoConvertida;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dataNascimentoConvertida = sdf.parse(dataNascimento);

        User novoUser = new User();
        novoUser.setNome(nome);
        novoUser.setUsername(username);
        novoUser.setEmail(email);
        novoUser.setSenha(senha); // Idealmente, a senha deve ser criptografada
        novoUser.setDataNascimento(dataNascimentoConvertida);

        userService.save(novoUser);

        return "redirect:/";
    }
}
