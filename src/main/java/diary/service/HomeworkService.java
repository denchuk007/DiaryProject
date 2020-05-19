package diary.service;

import diary.model.Classroom;
import diary.model.Homework;

import java.sql.Date;
import java.util.List;

public interface HomeworkService {

    void save(Homework homework);
    void delete(Homework homework);
    void delete(Long id);
    Homework findById(Long id);
    List<Homework> findAll();
    List<Homework> findByClassroomAndDate(Classroom classroom, Date date);
}
