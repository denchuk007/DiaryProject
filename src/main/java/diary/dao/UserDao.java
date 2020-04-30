package diary.dao;

import diary.model.Role;
import diary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User getByUsername(String username);
    List<User> findAll();
    List<User> findAllByRoles(Role role);
    List<User> findAllById(Long id);
    User findById(Long id);
    void deleteById(Long id);
}
