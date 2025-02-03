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

    @ManyToOne
    @JoinColumn(name = "codigoid_user", nullable = false)
    private User user;

    @Column(name = "legenda")
    private String legenda;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "data_post", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPost = new Date();
}

