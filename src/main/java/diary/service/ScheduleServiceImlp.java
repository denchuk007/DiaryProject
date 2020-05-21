package diary.service;

import diary.dao.ScheduleDao;
import diary.model.Classroom;
import diary.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImlp implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public void save(Schedule schedule) {
        scheduleDao.save(schedule);
    }

    @Override
    public void delete(Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    @Override
    public void delete(Long id) {
        scheduleDao.delete(id);
    }

    @Override
    public Schedule findById(Long id) {
        return scheduleDao.findOne(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleDao.findAll();
    }

    @Override
    public List<Schedule> findAllByClassroomAndWeek(Classroom classroom, int week) {
        return scheduleDao.findAllByClassroomAndWeekOrderByDayOfWeekAsc(classroom, week);
    }
}
