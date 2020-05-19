package diary.validator;

import diary.model.Homework;
import diary.model.Mark;
import diary.service.HomeworkService;
import diary.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HomeworkValidator implements Validator {

    @Autowired
    private HomeworkService homeworkService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Homework.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Homework homework = (Homework) o;

        if (homework.getText() == null) {
            errors.rejectValue("text", "Incorrect.Text");
        }

        if (homework.getDate() == null) {
            errors.rejectValue("date", "Incorrect.Date");
        }

        if (homework.getSubject() == null) {
            errors.rejectValue("text", "Incorrect.Subject");
        }

        Matcher matcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(homework.getDate().toString());
        if (homework.getDate() == null || !matcher.find()) {
            errors.rejectValue("date", "Incorrect.Date");
        }
    }
}
