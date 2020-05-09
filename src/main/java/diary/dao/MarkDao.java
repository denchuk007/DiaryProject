package diary.dao;

import diary.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MarkDao extends JpaRepository<Mark, Long> {

    List<Mark> findAllById(Long id);
}
