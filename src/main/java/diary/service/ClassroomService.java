package diary.service;

import diary.model.Classroom;

import java.util.List;

public interface ClassroomService {

    void save(Classroom classroom);
    void delete(Classroom classroom);
    void delete(Long id);
    List<Classroom> findAll();
    Classroom findByDigitAndWord(String digit, String word);
    Classroom findById(Long id);
}
