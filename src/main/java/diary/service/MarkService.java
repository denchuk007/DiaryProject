package diary.service;

import diary.model.Mark;
import diary.model.Subject;
import diary.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface MarkService {

    void save(Mark mark);
    void delete(Mark mark);
    void delete(Long id);
    List<Mark> findAll();
    Mark findById(Long id);
    Mark findByValueAndDateAndSubject(Long value, Date date, Subject subject, User pupil);
}
