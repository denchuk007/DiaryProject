package diary.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "mark")
    private Long value;

    @OneToOne(targetEntity = Subject.class)
    private Subject subject;

    @OneToOne(targetEntity = User.class)
    private User teacher;

    @ManyToOne(targetEntity = User.class)
    private User pupil;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Long getValue() {
        return value;
    }

    public Subject getSubject() {
        return subject;
    }

    public User getTeacher() {
        return teacher;
    }

    public User getPupil() {
        return pupil;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public void setPupil(User pupil) {
        this.pupil = pupil;
    }
}
