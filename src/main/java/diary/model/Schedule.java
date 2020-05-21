package diary.model;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "week")
    private int week;

    @Column(name = "day_of_week")
    private int dayOfWeek;

    @Column(name = "lesson")
    private int lesson;

    @Column(name = "cabinet")
    private String cabinet;

    @OneToOne(targetEntity = Classroom.class)
    private Classroom classroom;

    @OneToOne(targetEntity = Subject.class)
    private Subject subject;

    public Long getId() {
        return id;
    }

    public int getWeek() {
        return week;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getLesson() {
        return lesson;
    }

    public String getCabinet() {
        return cabinet;
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

    public void setWeek(int week) {
        this.week = week;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
