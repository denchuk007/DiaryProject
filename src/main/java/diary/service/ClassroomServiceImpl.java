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
    public List<Classroom> findAll() {
        return classroomDao.findAll();
    }
}
