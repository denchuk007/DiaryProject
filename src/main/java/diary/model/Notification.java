package diary.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(targetEntity = User.class)
    private User parent;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getParent() {
        return parent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public void setText(String text) {
        this.text = text;
    }
}
