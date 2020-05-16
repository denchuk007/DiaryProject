package diary.controller;

import diary.model.Mark;
import diary.model.User;
import diary.service.ClassroomService;
import diary.service.SecurityService;
import diary.service.UserService;
import diary.util.DiaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClassroomService classroomService;


    @RequestMapping(value = "/teacher/{pupilId}", method = RequestMethod.GET)
    public String teacher(@PathVariable("pupilId") Long pupilId,
                          Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("pupilId", pupilId);
        model.addAttribute("pupil", userService.findById(pupilId));

        return "pupil";
    }

    @RequestMapping(value = "/teacher/{pupilId}/{month}/{year}", method = RequestMethod.GET)
    public String teacher(@PathVariable("month") int month,
                          @PathVariable("year") int year,
                          @PathVariable("pupilId") Long pupilId,
                          Model model) {

        User currentUser = securityService.findLoggedInUser();
        User foundByIdUser = userService.findById(pupilId);
        Map<String, List<Mark>> table = new HashMap<>();
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        String[] subjectsTitle = DiaryUtil.getPupilMarks(currentUser, foundByIdUser, month, year, table, lengthOfMonth, 0, pupilId).first;
        Mark[][] marksTable = DiaryUtil.getPupilMarks(currentUser, foundByIdUser, month, year, table, lengthOfMonth, 0, pupilId).second;

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("marksTable", marksTable);
        model.addAttribute("subjectsTitle", subjectsTitle);
        model.addAttribute("subjectsCount", table.size());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("lengthOfMonth", lengthOfMonth);
        model.addAttribute("pupilId", pupilId);
        model.addAttribute("pupil", userService.findById(pupilId));
        model.addAttribute("daysOfWeek", DiaryUtil.getDaysOfWeek(year, month, lengthOfMonth));

        return "pupil";
    }

    @RequestMapping(value = "/classrooms", method = RequestMethod.GET)
    public String classrooms(Model model) {
        User currentUser =  securityService.findLoggedInUser();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());

        return "classrooms";
    }

    @RequestMapping(value = "/classroom/{classroom}", method = RequestMethod.GET)
    public String classrooms( @PathVariable("classroom") String classroom, Model model) {
        User currentUser =  securityService.findLoggedInUser();
        String classroomDigit = String.join("", classroom.split("\\D"));
        String classroomWord = String.join("", classroom.split("\\d"));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classroom", classroomService.findByDigitAndWord(classroomDigit, classroomWord));
        model.addAttribute("pupils", userService.findAllByClassroom(classroomService.findByDigitAndWord(classroomDigit, classroomWord)));

        return "classroom";
    }
}
