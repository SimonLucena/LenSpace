package br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "lenspace", name = "comment_like")
public class CommentLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;

    @ManyToOne
    @JoinColumn(name = "codigoid_comment", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "codigoid_user", nullable = false)
    private User user;
}
