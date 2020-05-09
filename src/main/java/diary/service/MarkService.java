package diary.service;

import diary.model.Mark;

import java.util.List;
import java.util.Set;

public interface MarkService {

    void save(Mark mark);
    List<Mark> findAll();
    List<Mark> findAllById(Long id);
}
