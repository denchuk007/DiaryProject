package diary.model;

import javax.persistence.*;
import java.util.List;
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
    private String birthday;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinTable(name = "classroom_user", joinColumns = @JoinColumn(name = "classroom_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Classroom classroom;

    @ManyToOne
    @JoinTable(name = "classroom_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id"))
    private Classroom classroom;

    @ManyToMany(mappedBy = "pupils", fetch = FetchType.EAGER)
    private Set<User> pupil;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "parent_pupil", joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "pupil_id"))
    private Set<User> pupils;

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

    public String getBirthday() {
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

    public Set<User> getPupil() {
        return pupil;
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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setPupils(Set<User> pupils) {
        this.pupils = pupils;
    }

    public void setPupil(Set<User> pupil) {
        this.pupil = pupil;
    }
}
