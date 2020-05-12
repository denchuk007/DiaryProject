package diary.service;

import diary.model.Subject;

import java.util.List;

public interface SubjectService {

    void delete(Subject subject);
    void delete(Long id);
    void save(Subject subject);
    List<Subject> findAll();
    Subject findById(Long id);
    Subject findByTitle(String title);
}
