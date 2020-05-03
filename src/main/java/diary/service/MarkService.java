package diary.service;

import diary.model.Mark;

import java.util.List;

public interface MarkService {

    void save(Mark mark);
    List<Mark> findAll();
}
