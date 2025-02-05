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
@Table(schema = "lenspace", name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;
    public Long getCodigoid() {return codigoid;}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigoid_user", nullable = false)
    private User user;
    public void setUser(User user){this.user = user;}
    public User getUser() {return user;}

    @Column(name = "legenda")
    private String legenda;
    public void setLegenda(String legenda){this.legenda = legenda;}
    public String getLegenda(){return legenda;}

    @Column(name = "imagem")
    public String imagem;
    public void setImagem(String imagem) {this.imagem = imagem;}

    @Column(name = "data_post", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPost = new Date();
}

