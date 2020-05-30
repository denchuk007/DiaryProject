package diary.service;

import diary.dao.RoleDao;
import diary.dao.UserDao;
import diary.model.Classroom;
import diary.model.Role;
import diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SecurityService securityService;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getByUserName(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public List<User> findAllByRole(Long roleId) {
        Role role = roleDao.findOne(roleId);
        return userDao.findAllByRoles(role);
    }

    @Override
    public List<User> findAllByClassroom(Classroom classroom) {
        return userDao.findAllByClassroom(classroom);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userDao.findById(userId) != null) {
            userDao.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAllCurrentTeacherPupils() {
        User user = securityService.findLoggedInUser();
        return userDao.findAllByClassroom(user.getClassroom());
    }
}
