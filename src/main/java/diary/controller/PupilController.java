package diary.controller;

import diary.model.Mark;
import diary.model.User;
import diary.service.ClassroomService;
import diary.service.SecurityService;
import diary.service.SubjectService;
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
public class PupilController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/pupil", method = RequestMethod.GET)
    public String pupil(Model model) {
        User currentUser = securityService.findLoggedInUser();
        model.addAttribute("currentUserMarks", currentUser.getMarks());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("teachers", userService.findAllByRole(3L));
        model.addAttribute("subjects", subjectService.findAll());

        return "pupil";
    }

    @RequestMapping(value = "/pupil/{month}/{year}", method = RequestMethod.GET)
    public String pupil(@PathVariable("month") int month,
                        @PathVariable("year") int year, Model model) {

        User currentUser = securityService.findLoggedInUser();
        Map<String, List<Mark>> table = new HashMap<>();
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        String[] subjectsTitle = DiaryUtil.getPupilMarks(currentUser, null, month, year, table, lengthOfMonth, 0, 0L).first;
        Mark[][] marksTable = DiaryUtil.getPupilMarks(currentUser, null, month, year, table, lengthOfMonth, 0, 0L).second;

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("marksTable", marksTable);
        model.addAttribute("subjectsTitle", subjectsTitle);
        model.addAttribute("subjectsCount", table.size());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("lengthOfMonth", lengthOfMonth);

        return "pupil";
    }
}
