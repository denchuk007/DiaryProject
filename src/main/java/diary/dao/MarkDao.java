package diary.dao;

import diary.model.Mark;
import diary.model.Subject;
import diary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface MarkDao extends JpaRepository<Mark, Long> {

    Mark findById(Long id);
    Mark findByDateAndSubjectAndPupil(Date date, Subject subject, User pupil);
}
