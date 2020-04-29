package diary.model;

import javax.persistence.*;

@Entity
@Table(name = "marks")
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

    @Column(name = "pupil_id")
    private String pupilId;

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

    public String getPupilId() {
        return pupilId;
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

    public void setPupilId(String pupilId) {
        this.pupilId = pupilId;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", value=" + value +
                ", subjectId=" + subjectId +
                ", teacherId='" + teacherId + '\'' +
                ", pupilId='" + pupilId + '\'' +
                '}';
    }
}
