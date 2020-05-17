package diary.service;

import diary.model.News;

import java.util.List;

public interface NewsService {

    void save(News news);
    void delete(News news);
    void delete(Long id);
    News findById(Long id);
    List<News> findAll();
}
