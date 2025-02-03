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
@Table(schema = "lenspace", name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;

    @ManyToOne
    @JoinColumn(name = "codigoid_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private String comentario;

    @Column(name = "data_comment", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataComment = new Date();
}
