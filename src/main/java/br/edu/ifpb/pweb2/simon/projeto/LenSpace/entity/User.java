package br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "lenspace", name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;
    public Long getCodigoid() {
        return codigoid;
    }

    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    public void setEmail(String email) {
        this.email = email;
    }

    @Setter
    @Column(name = "senha", nullable = false)
    private String senha;
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Setter
    @Column(name = "nome", nullable = false)
    private String nome;
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Getter
    @Setter
    @Column(name = "username", nullable = false)
    public String username;
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "data_nascimento")
    @Setter
    private Date dataNascimento;
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Column(name = "administrador", nullable = false)
    @Setter
    private boolean administrador = false;

    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private Date dataCriacao = new Date();
}
