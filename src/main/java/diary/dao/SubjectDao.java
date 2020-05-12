package diary.dao;

import diary.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectDao extends JpaRepository<Subject, Long> {

    Subject findAllById(Long id);
    Subject findByTitle(String title);
}
