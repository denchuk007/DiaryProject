package diary.validator;

import diary.model.Classroom;
import diary.model.Mark;
import diary.service.ClassroomService;
import diary.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MarkValidator implements Validator {

    @Autowired
    private MarkService markService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Mark mark = (Mark) o;

        if (mark.getValue() == null) {
            errors.rejectValue("value", "Incorrect.Value");
        }

        if (mark.getSubject() == null) {
            errors.rejectValue("value", "Incorrect.Subject");
        }

        Matcher matcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(mark.getDate().toString());
        if (mark.getDate() == null || !matcher.find()) {
            errors.rejectValue("value", "Incorrect.Date");
        }

        if (markService.findByDateAndSubjectAndPupil(mark.getDate(), mark.getSubject(), mark.getPupil()) != null) {
            errors.rejectValue("value", "Mark.Is.Busy");
        }
    }
}
