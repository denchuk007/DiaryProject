package diary.dao;

import diary.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkDao extends JpaRepository<Mark, Long> {
    List<Mark> findAllByPupilId(String id);
}
