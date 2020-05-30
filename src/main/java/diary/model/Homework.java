package diary.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "link")
    private String link;

    @Column(name = "date")
    private Date date;

    @OneToOne(targetEntity = Subject.class)
    private Subject subject;

    @OneToOne(targetEntity = Classroom.class)
    private Classroom classroom;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public Date getDate() {
        return date;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
