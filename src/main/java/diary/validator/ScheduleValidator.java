package diary.validator;

import diary.model.Schedule;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ScheduleValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Schedule.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Schedule schedule = (Schedule) o;

        if (schedule.getInterval() == null || schedule.getInterval().length() > 20 || schedule.getInterval().length() < 1) {
            errors.rejectValue("cabinet", "Incorrect.Interval");
        }

        if (schedule.getCabinet() == null || schedule.getCabinet().length() > 10 || schedule.getCabinet().length() < 1) {
            errors.rejectValue("cabinet", "Incorrect.Cabinet");
        }

        if (schedule.getClassroom() == null) {
            errors.rejectValue("cabinet", "Classroom.Not.Found");
        }

        if (schedule.getSubject() == null) {
            errors.rejectValue("cabinet", "Subject.Not.Found");
        }
    }
}
