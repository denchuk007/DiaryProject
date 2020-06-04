package diary.service;

import diary.dao.ClassroomDao;
import diary.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomDao classroomDao;

    @Override
    public void save(Classroom classroom) {
        classroom.setDigit(classroom.getDigit());
        classroom.setWord(classroom.getWord());
        classroomDao.save(classroom);
    }

    @Override
    public void delete(Classroom classroom) {
        classroomDao.delete(classroom);
    }

    @Override
    public void delete(Long id) {
        classroomDao.delete(id);
    }

    @Override
    public List<Classroom> findAll() {
        return classroomDao.findAll();
    }

    @Override
    public List<Classroom> findAllByDigit(String digit) {
        return classroomDao.findAllByDigit(digit);
    }

    @Override
    public Classroom findByDigitAndWord(String digit, String word) {
        return classroomDao.findByDigitAndWord(digit, word);
    }

    @Override
    public Classroom findById(Long id) {
        return classroomDao.findById(id);
    }
}
