package diary.service;

import diary.dao.MarkDao;
import diary.model.Mark;
import diary.model.Subject;
import diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    private MarkDao markDao;

    @Override
    public void save(Mark mark) {
        mark.setDate(mark.getDate());
        mark.setSubject(mark.getSubject());
        mark.setPupil(mark.getPupil());
        mark.setTeacher(mark.getTeacher());
        mark.setValue(mark.getValue());
        markDao.save(mark);
    }

    @Override
    public void delete(Mark mark) {
        markDao.delete(mark);
    }

    @Override
    public void delete(Long id) {
        markDao.delete(id);
    }

    @Override
    public List<Mark> findAll() {
        return markDao.findAll();
    }

    @Override
    public Mark findById(Long id) {
        return markDao.findById(id);
    }

    @Override
    public Mark findByDateAndSubjectAndPupil(Date date, Subject subject, User pupil) {
        return markDao.findByDateAndSubjectAndPupil(date, subject, pupil);
    }
}
