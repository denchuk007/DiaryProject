package diary.service;

import diary.model.Mark;
import diary.model.Role;
import diary.model.User;

import java.util.List;

public interface UserService {

    void save(User user, Long roleId);
    User findByUsername(String username);
    User getByUserName(String username);
    List<User> findAllUsers();
    List<Role> findAllRoles();
    void setTeacherToTheClassroom(Long classroomId, Long teacherId);
    void setPupilToTheClassroom(Long classroomId, Long pupilId);
    void setPupilToTheParent(Long parentId, Long pupilId);
    List<User> findAllPupils();
    List<Mark> findAllCurrentPupilMarks();
    boolean deleteUser(String userId);
}
