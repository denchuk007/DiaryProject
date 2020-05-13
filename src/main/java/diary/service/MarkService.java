package diary.service;

import diary.model.Mark;

import java.util.List;
import java.util.Set;

public interface MarkService {

    void save(Mark mark);
    void delete(Mark mark);
    void delete(Long id);
    List<Mark> findAll();
    Mark findById(Long id);
}
