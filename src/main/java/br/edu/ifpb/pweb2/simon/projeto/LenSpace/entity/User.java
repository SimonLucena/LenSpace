package br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "lenspace", name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;
    public Long getCodigoid() {
        return codigoid;
    }

    @Setter
    @Column(name = "email", nullable = false, unique = true)
    public String email;
    public void setEmail(String email) {
        this.email = email;
    }

    @Setter
    @Column(name = "senha", nullable = false)
    private String senha;
    public void setSenha(String senha) {
        this.senha = senha;
    }
    @Override
    public String getPassword() {
        return this.senha;
    }

    @Setter
    @Column(name = "nome", nullable = false)
    private String nome;
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() { return this.nome; }

    @Getter
    @Setter
    @Column(name = "username", nullable = false)
    public String username;
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getUsername() {
        return this.username;
    }

    @Column(name = "data_nascimento")
    @Setter
    private Date dataNascimento;
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Column(name = "administrador", nullable = false)
    @Setter
    @Getter
    private boolean administrador = false;
    public void setAdministrador(boolean administrador) {this.administrador = administrador;}
    public boolean isAdministrador() {return this.administrador;}

    @Column(name = "data_criacao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private Date dataCriacao = new Date();
    public Date getDataCriacao() {return this.dataCriacao;}
    public String getDataCriacaoToString() {
        String retorno = String.format("%s/%s/%s", this.dataCriacao.getDay(), this.dataCriacao.getMonth(), this.dataCriacao.getYear());
        return retorno;
    }

    @Column(name = "ativo", nullable = false)
    @Setter
    private boolean ativo = true;
    public void setAtivo(boolean ativo) {this.ativo = ativo;}
    @Override
    public boolean isEnabled() {return this.ativo;}



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
