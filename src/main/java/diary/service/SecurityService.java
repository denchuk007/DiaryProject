package diary.service;

import diary.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {

    UserDetails findLoggedInUsername();

    void autoLogin(String username, String password);
}
