package diary.dao;

import diary.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomDao extends JpaRepository<Classroom, Long> {
    List<Classroom> findAll();
    Classroom findByDigitAndWord(String digit, String word);
}
