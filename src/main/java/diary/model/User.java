package diary.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private Date birthday;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToOne
    @JoinTable(name = "classroom_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id"))
    private Classroom classroom;

    @ManyToMany(mappedBy = "pupils")
    private Set<User> parents;

    @OrderBy("id")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "parent_pupil", joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "pupil_id"))
    private Set<User> pupils;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "pupil_id", updatable = false)
    private Set<Mark> marks;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Set<User> getPupils() {
        return pupils;
    }

    public Set<User> getParents() {
        return parents;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setPupils(Set<User> pupils) {
        this.pupils = pupils;
    }

    public void setParents(Set<User> parents) {
        this.parents = parents;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }
}
