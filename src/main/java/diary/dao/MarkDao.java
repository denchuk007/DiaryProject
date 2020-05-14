package diary.dao;

import diary.model.Mark;
import diary.model.Subject;
import diary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface MarkDao extends JpaRepository<Mark, Long> {

    Mark findById(Long id);
    Mark findByValueAndDateAndSubjectAndPupil(Long value, Date date, Subject subject, User pupil);
}
