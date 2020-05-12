package diary.service;

import diary.dao.SubjectDao;
import diary.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Override
    public void delete(Subject subject) {
        subjectDao.delete(subject);
    }

    @Override
    public void delete(Long id) {
        subjectDao.delete(id);
    }

    @Override
    public void save(Subject subject) {
        subject.setTitle(subject.getTitle());
        subjectDao.save(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectDao.findAll();
    }

    @Override
    public Subject findById(Long id) {
        return subjectDao.findOne(id);
    }

    @Override
    public Subject findByTitle(String title) {
        return subjectDao.findByTitle(title);
    }
}
