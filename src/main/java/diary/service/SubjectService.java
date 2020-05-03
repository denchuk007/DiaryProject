package diary.service;

import diary.model.Subject;

import java.util.List;

public interface SubjectService {

    void save(Subject subject);
    List<Subject> findAll();
    Subject findById(Long id);
}
