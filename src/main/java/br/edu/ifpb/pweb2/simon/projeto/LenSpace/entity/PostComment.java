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
@Table(schema = "lenspace", name = "post_comment")
public class PostComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;

    @ManyToOne
    @JoinColumn(name = "codigoid_post", nullable = false)
    private Post post;
    public void setPost(Post post) {this.post = post;}

    @ManyToOne
    @JoinColumn(name = "codigoid_comment", nullable = false)
    private Comment comment;
    public void setComment(Comment comment) {this.comment = comment;}
}
