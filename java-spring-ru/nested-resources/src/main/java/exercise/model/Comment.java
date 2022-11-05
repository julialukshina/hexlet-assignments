package exercise.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.FetchType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    // BEGIN
    @Id
    private long id;

    @Lob
    private String content;

    // Связанная сущность
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    // END
}
