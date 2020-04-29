package diary.model;

import javax.persistence.*;

@Entity
@Table(name = "classrooms")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "digit")
    private String digit;

    @Column(name = "word")
    private String word;

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

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", digit='" + digit + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
