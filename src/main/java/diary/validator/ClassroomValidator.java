package diary.validator;

import diary.model.Classroom;
import diary.model.User;
import diary.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClassroomValidator implements Validator {

    @Autowired
    private ClassroomService classroomService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Classroom.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Classroom classroom = (Classroom) o;

        if (classroom.getDigit().length() < 1 || classroom.getDigit().length() > 2) {
            errors.rejectValue("digit", "Incorrect.Digit");
        }

        if (classroom.getWord().length() != 1) {
            errors.rejectValue("word", "Incorrect.Word");
        }

        if (classroomService.findByDigitAndWord(classroom.getDigit(), classroom.getWord()) != null) {
            errors.rejectValue("word", "Classroom.Is.Busy");
        }
    }
}
