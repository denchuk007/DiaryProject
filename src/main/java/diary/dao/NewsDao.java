package diary.dao;

import diary.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsDao extends JpaRepository<News, Long> {

    List<News> findAllByIdNotOrderByDateDesc(Long id);
}
