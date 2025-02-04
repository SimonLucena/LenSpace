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
@Table(schema = "lenspace", name = "user_follow")
public class UserFollow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoid;

    @ManyToOne
    @JoinColumn(name = "codigoid_user", nullable = false)
    private User user;
    public void setUser(User user) {this.user = user;}

    @ManyToOne
    @JoinColumn(name = "codigoid_follow", nullable = false)
    private User follow;
    public void setFollow(User follow) {this.follow = follow;}
}
