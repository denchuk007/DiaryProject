package diary.validator;

import diary.model.Subject;
import diary.model.User;
import diary.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SubjectValidator implements Validator {

    @Autowired
    private SubjectService subjectService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Subject.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Subject subject = (Subject) o;

        if (subject.getTitle().length() < 1 || subject.getTitle().length() > 32) {
            errors.rejectValue("title", "Incorrect.Title");
        }

        if (subjectService.findByTitle(subject.getTitle()) != null) {
            errors.rejectValue("title", "Title.Is.Busy");
        }
    }
}
