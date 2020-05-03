package diary.model;

import java.util.Set;

public interface IUser {

    public Long getId();

    public String getUsername();

    public String getPassword();

    public String getName();

    public String getSurname();

    public String getBirthday();

    public String getConfirmPassword();

    public Set<Role> getRoles();

    public Classroom getClassroom();

    public Set<User> getPupils();

    public Set<User> getParents();

    public Set<Mark> getMarks();

    public void setId(Long id);

    public void setUsername(String username);

    public void setPassword(String password);

    public void setConfirmPassword(String confirmPassword);

    public void setRoles(Set<Role> roles);

    public void setName(String name);

    public void setSurname(String surname);

    public void setBirthday(String birthday);

    public void setClassroom(Classroom classroom);

    public void setPupils(Set<User> pupils);

    public void setParents(Set<User> parents);

    public void setMarks(Set<Mark> marks);
}
