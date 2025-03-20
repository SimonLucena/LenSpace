package br.edu.ifpb.pweb2.simon.projeto.LenSpace.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "lenspace", name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long codigoid;
    public Long getCodigoid() {
        return codigoid;
    }

    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    public String tag;
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }
}
