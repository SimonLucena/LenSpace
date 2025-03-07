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
@Table(schema = "lenspace", name = "post_like")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;

    @ManyToOne
    @JoinColumn(name = "codigoid_post", nullable = false)
    private Post post;
    public Post getPost() {return post;}
    public void setPost(Post post) {this.post = post;}

    @ManyToOne
    @JoinColumn(name = "codigoid_user_like", nullable = false)
    private User user;
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
