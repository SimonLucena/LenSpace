package br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "lenspace", name = "post_tag")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;

    @ManyToOne
    @JoinColumn(name = "codigoid_tag", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "codigoid_post", nullable = false)
    private Post post;
}
