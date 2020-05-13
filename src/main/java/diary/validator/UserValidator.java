package diary.validator;

import diary.model.User;
import diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getUsername().length() < 1 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Incorrect.Login");
        }

        if (user.getName().length() < 1 || user.getName().length() > 32) {
            errors.rejectValue("name", "Incorrect.Name");
        }

        if (user.getSurname().length() < 1 || user.getSurname().length() > 32) {
            errors.rejectValue("surname", "Incorrect.Surname");
        }

        String str = user.getBirthday().toString();
        Matcher matcher = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$").matcher(user.getBirthday().toString());
        if (!matcher.find()) {
            errors.rejectValue("birthday", "Incorrect.Birthday");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Username.Is.Busy");
        }

        if (user.getPassword().length() < 1 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Incorrect.Password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Confirm.Password.Failed");
        }
    }
}
