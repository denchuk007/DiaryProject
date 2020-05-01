package diary.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "digit")
    private String digit;

    @Column(name = "word")
    private String word;

    @OneToMany(mappedBy = "classroom")
    private Set<User> user;

    public Long getId() {
        return id;
    }

    public String getDigit() {
        return digit;
    }

    public String getWord() {
        return word;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", digit='" + digit + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
