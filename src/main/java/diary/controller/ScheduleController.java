package diary.controller;

import diary.model.Classroom;
import diary.model.Homework;
import diary.model.Schedule;
import diary.model.Subject;
import diary.service.*;
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
                                 BindingResult bindingResult, Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("subjects", subjectService.findAll());

        schedule.setClassroom(classroomService.findById(classroomId));
        schedule.setDayOfWeek(dayOfWeek);
        schedule.setSubject(subjectService.findById(subjectId));
        schedule.setWeek(week);

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
        model.addAttribute("firstWeekSchedule", scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 1));
        model.addAttribute("secondWeekSchedule", scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 2));

        List<Schedule> scheduleList = scheduleService.findAllByClassroomAndWeek(classroomService.findByDigitAndWord(classroomDigit, classroomWord), 1);

        String[][] table = new String[30][3];

        for (int i = 0; i < 30; i++) {
            if (!scheduleList.isEmpty()) {
                if (i < 6) {
                    if (scheduleList.get(0).getDayOfWeek() == 1) {
                        addToTable(table, scheduleList, i);
                    }
                } else if (i < 12) {
                    if (scheduleList.get(0).getDayOfWeek() == 2) {
                        addToTable(table, scheduleList, i);
                    }
                } else if (i < 18) {
                    if (scheduleList.get(0).getDayOfWeek() == 3) {
                        addToTable(table, scheduleList, i);
                    }
                } else if (i < 24) {
                    if (scheduleList.get(0).getDayOfWeek() == 4) {
                        addToTable(table, scheduleList, i);
                    }
                } else {
                    if (scheduleList.get(0).getDayOfWeek() == 5) {
                        addToTable(table, scheduleList, i);
                    }
                }
            }
        }

        model.addAttribute("table", table);

        return "schedule";
    }

    public static void addToTable(String[][] table, List<Schedule> scheduleList, int i) {
        table[i][0] = scheduleList.get(0).getInterval();
        table[i][1] = scheduleList.get(0).getSubject().getTitle();
        table[i][2] = scheduleList.get(0).getCabinet();
        scheduleList.remove(0);
    }
}
