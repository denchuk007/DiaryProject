package diary.service;

import diary.dao.HomeworkDao;
import diary.model.Classroom;
import diary.model.Homework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkDao homeworkDao;

    @Override
    public void save(Homework homework) {
        homeworkDao.save(homework);
    }

    @Override
    public void delete(Homework homework) {
        homeworkDao.delete(homework);
    }

    @Override
    public void delete(Long id) {
            homeworkDao.delete(id);
    }

    @Override
    public Homework findById(Long id) {
        return homeworkDao.findOne(id);
    }

    @Override
    public List<Homework> findAll() {
        return homeworkDao.findAll();
    }

    @Override
    public List<Homework> findByClassroomAndDate(Classroom classroom, Date date) {
        return homeworkDao.findByClassroomAndDate(classroom, date);
    }
}
