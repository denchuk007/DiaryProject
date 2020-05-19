package diary.dao;

import diary.model.Classroom;
import diary.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface HomeworkDao extends JpaRepository<Homework, Long> {

    List<Homework> findByClassroomAndDate(Classroom classroom, Date date);
}
