package diary.service;

import diary.model.Subject;
import diary.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {

    UserDetails findLoggedInUsername();
    User findLoggedInUser();
    void autoLogin(String username, String password);
}
