package diary.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "mark")
    private Long value;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "teacher_id")
    private String teacherId;

    @ManyToOne(targetEntity = User.class)
    private User pupil;

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Long getValue() {
        return value;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public User getPupil() {
        return pupil;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public void setPupil(User pupil) {
        this.pupil = pupil;
    }
}
