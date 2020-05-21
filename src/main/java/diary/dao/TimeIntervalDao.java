package diary.dao;

import diary.model.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeIntervalDao extends JpaRepository<TimeInterval, Long> {
}
