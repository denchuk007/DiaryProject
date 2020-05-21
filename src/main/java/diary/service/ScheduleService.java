package diary.service;

import diary.model.Classroom;
import diary.model.Schedule;

import java.util.List;

public interface ScheduleService {

    void save(Schedule schedule);
    void delete(Schedule schedule);
    void delete(Long id);
    Schedule findById(Long id);
    List<Schedule> findAll();
    List<Schedule> findAllByClassroomAndWeek(Classroom classroom, int week);
}
