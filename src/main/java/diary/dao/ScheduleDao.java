package diary.dao;

import diary.model.Classroom;
import diary.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleDao extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByClassroomAndWeekOrderByDayOfWeekAscLessonAsc(Classroom classroom, int week);
}
