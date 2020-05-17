package diary.service;

import diary.dao.NewsDao;
import diary.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public void save(News news) {
        newsDao.save(news);
    }

    @Override
    public void delete(News news) {
        newsDao.delete(news);
    }

    @Override
    public void delete(Long id) {
        newsDao.delete(id);
    }

    @Override
    public News findById(Long id) {
        return newsDao.findOne(id);
    }

    @Override
    public List<News> findAll() {
        return newsDao.findAll();
    }
}
