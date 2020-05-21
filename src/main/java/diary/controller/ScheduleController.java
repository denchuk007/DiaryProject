package diary.controller;

import diary.dao.TimeIntervalDao;
import diary.model.Schedule;
import diary.model.TimeInterval;
import diary.service.*;
import diary.util.DiaryUtil;
import diary.validator.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ScheduleValidator scheduleValidator;

    @Autowired
    private TimeIntervalDao timeIntervalDao;

    @RequestMapping(value = "/new-schedule", method = RequestMethod.GET)
    public String createSchedule(Model model) {
        model.addAttribute("scheduleForm", new Schedule());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        return "new-schedule";
    }

    @RequestMapping(value = "/new-schedule", method = RequestMethod.POST)
    public String createSchedule(@ModelAttribute("scheduleForm") Schedule schedule,
                                 @RequestParam("subjectId") Long subjectId,
                                 @RequestParam("classroomId") Long classroomId,
                                 @RequestParam("week") int week,
                                 @RequestParam("dayOfWeek") int dayOfWeek,
                                 @RequestParam("lesson") int lesson,
                                 BindingResult bindingResult, Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        schedule.setClassroom(classroomService.findById(classroomId));
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setSubject(subjectService.findById(subjectId));
        schedule.setWeek(week);
        schedule.setLesson(lesson);

        scheduleValidator.validate(schedule, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-schedule";
        }

        scheduleService.save(schedule);

        return "redirect:/new-schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String schedule(Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());

        return "schedule";
    }

    @RequestMapping(value = "/schedule/{classroom}", method = RequestMethod.GET)
    public String schedule(@PathVariable("classroom") String classroom, Model model) {

        String classroomDigit = String.join("", classroom.split("\\D"));
        String classroomWord = String.join("", classroom.split("\\d"));

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("firstWeekSchedule", scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 1));
        model.addAttribute("secondWeekSchedule", scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 2));

        List<Schedule> firstWeekScheduleList = scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 1);
        List<Schedule> secondWeekScheduleList = scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 2);
        List<TimeInterval> timeIntervalList = timeIntervalDao.findAll();

        model.addAttribute("firstWeek", DiaryUtil.getScheduleTable(firstWeekScheduleList, timeIntervalList));
        model.addAttribute("secondWeek", DiaryUtil.getScheduleTable(secondWeekScheduleList, timeIntervalList));

        return "schedule";
    }

    @RequestMapping(value = "/edit/schedule/{scheduleId}", method = RequestMethod.GET)
    public String editMark(@PathVariable("scheduleId") Long scheduleId,
                           Model model) {

        Schedule currentSchedule = scheduleService.findById(scheduleId);
        model.addAttribute("scheduleForm", currentSchedule);
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        return "new-schedule";
    }

    @RequestMapping(value = "/edit/schedule/{scheduleId}", method = RequestMethod.POST)
    public String createSchedule(@ModelAttribute("scheduleForm") Schedule schedule,
                                 @PathVariable("scheduleId") Long scheduleId,
                                 @RequestParam("subjectId") Long subjectId,
                                 @RequestParam("classroomId") Long classroomId,
                                 @RequestParam("week") int week,
                                 @RequestParam("dayOfWeek") int dayOfWeek,
                                 @RequestParam("lesson") int lesson,
                                 BindingResult bindingResult, Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        schedule.setClassroom(classroomService.findById(classroomId));
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setSubject(subjectService.findById(subjectId));
        schedule.setWeek(week);
        schedule.setLesson(lesson);

        scheduleValidator.validate(schedule, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-schedule";
        }

        scheduleService.save(schedule);

        return "redirect:/new-schedule";
    }

    @RequestMapping(value = "/remove/schedule/{scheduleId}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("scheduleId") Long scheduleId) {

        scheduleService.delete(scheduleId);

        return "redirect:/schedule";
    }
}
