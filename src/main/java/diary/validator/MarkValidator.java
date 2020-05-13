package diary.validator;

import diary.model.Classroom;
import diary.model.Mark;
import diary.service.ClassroomService;
import diary.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if (mark.getValue().toString().length() < 1 || mark.getValue().toString().length() > 3) {
            errors.rejectValue("value", "Incorrect.Value");
        }
    }
}
