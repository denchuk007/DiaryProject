package diary.service;

import diary.dao.ClassroomDao;
import diary.dao.MarkDao;
import diary.dao.RoleDao;
import diary.dao.UserDao;
import diary.model.Classroom;
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
    private MarkDao markDao;

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(User user, Long roleId, Long classroomId, Long pupilId) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(roleId));
        user.setRoles(roles);
        if (classroomId != 0 && pupilId == 0) {
            user.setClassroom(classroomDao.findOne(classroomId));
        }

        if (pupilId != 0) {
            Set<User> set = new HashSet<>();
            set.add(userDao.findOne(pupilId));
            user.setPupils(set);
        }

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
//        User user = securityService.findLoggedInUser();
//        List<Object> results = entityManager.createNativeQuery(
//                "SELECT classroom_id FROM classroom_teacher WHERE classroom_teacher.teacher_id = " + user.getId().toString()).getResultList();
//        Long classroomId = Long.valueOf(results.get(0).toString());
//
//        List<Object> results2 = entityManager.createNativeQuery(
//                "SELECT pupil_id FROM classroom_pupil WHERE classroom_pupil.classroom_id = " + classroomId.toString()).getResultList();
//
//        List<User> pupils = new ArrayList<>();
//        for (Object pupilId : results2) {
//            pupils.add(userDao.findById(Long.valueOf(pupilId.toString())));
//        }
//
//        return pupils;
        User user = securityService.findLoggedInUser();
        return userDao.findAllByClassroom(user.getClassroom());
    }
}
