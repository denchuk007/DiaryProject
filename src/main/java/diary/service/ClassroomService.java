package diary.service;

import diary.model.Classroom;

import java.util.List;

public interface ClassroomService {

    void save(Classroom classroom);
    List<Classroom> findAll();
    Classroom findByDigitAndWord(String digit, String word);
}
