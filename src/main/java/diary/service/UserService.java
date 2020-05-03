package diary.service;

import diary.model.Mark;
import diary.model.Role;
import diary.model.User;

import java.util.List;

public interface UserService {

    void save(User user, Long roleId, Long classroomId, Long pupilId);
    void update(User user);
    User findByUsername(String username);
    User findById(Long id);
    User getByUserName(String username);
    List<User> findAllUsers();
    List<Role> findAllRoles();
    List<User> findAllByRole(Long roleId);
    boolean deleteUser(Long userId);
    List<User> findAllCurrentTeacherPupils();
}
