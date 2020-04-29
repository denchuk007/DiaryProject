package diary.service;

import diary.dao.RoleDao;
import diary.dao.UserDao;
import diary.model.Role;
import diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(User user, Long roleId) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(roleId));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
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
    @Transactional
    public void setTeacherToTheClassroom(Long classroomId, Long teacherId) {
        entityManager.createNativeQuery("INSERT INTO classroom_teacher (classroom_id, teacher_id) VALUES (?,?)")
                .setParameter(1, classroomId)
                .setParameter(2, teacherId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void setPupilToTheClassroom(Long classroomId, Long pupilId) {
        entityManager.createNativeQuery("INSERT INTO classroom_pupil (classroom_id, pupil_id) VALUES (?,?)")
                .setParameter(1, classroomId)
                .setParameter(2, pupilId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void setPupilToTheParent(Long parentId, Long pupilId) {
        entityManager.createNativeQuery("INSERT INTO parent_pupil (parent_id, pupil_id) VALUES (?,?)")
                .setParameter(1, parentId)
                .setParameter(2, pupilId)
                .executeUpdate();
    }

    @Override
    public List<User> findAllPupils() {
//        entityManager.createNativeQuery("SELECT username FROM users, user_roles WHERE users.id == user_roles.id")
//                .executeUpdate();
//        List<Object> results = entityManager.createNativeQuery("SELECT username FROM users, user_roles WHERE users.id == user_roles.id").getResultList();
        Role pupilRole = roleDao.findOne(1L);
        return userDao.findAllByRoles(pupilRole);
    }
}
