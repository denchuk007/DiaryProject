package diary.service;

import diary.model.User;

import java.util.List;

/**
 * Service class for {@link User}
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User getByUserName(String username);

    List<User> findAll();
}
