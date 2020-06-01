package diary.controller;

import diary.model.Mark;
import diary.model.User;
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
public class ParentController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/parent", method = RequestMethod.GET)
    public String parent(Model model) {
        User currentUser =  securityService.findLoggedInUser();
        User[] pupils = DiaryUtil.getPupils(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("currentUserPupils", pupils);

        return "parent";
    }

    @RequestMapping(value = "/parent/{pupilNumber}", method = RequestMethod.GET)
    public String parent(@PathVariable("pupilNumber") int pupilNumber,
                         Model model) {
        User currentUser = securityService.findLoggedInUser();
        User[] pupils = DiaryUtil.getPupils(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("pupilNumber", pupilNumber);
        model.addAttribute("pupil", pupils[pupilNumber - 1]);
        model.addAttribute("parentsPupilId", pupils[pupilNumber].getId());

        return "pupil";
    }



    @RequestMapping(value = "/parent/{pupilNumber}/{month}/{year}", method = RequestMethod.GET)
    public String parent(@PathVariable("month") int month,
                         @PathVariable("year") int year,
                         @PathVariable("pupilNumber") int pupilNumber,
                         Model model) {

        User currentUser = securityService.findLoggedInUser();
        User[] pupils = DiaryUtil.getPupils(currentUser);

        Map<String, List<Mark>> table = new HashMap<>();
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        String[] subjectsTitle = DiaryUtil.getPupilMarks(currentUser, null, month, year, table, lengthOfMonth, pupilNumber, 0L).first;
        Mark[][] marksTable = DiaryUtil.getPupilMarks(currentUser, null, month, year, table, lengthOfMonth, pupilNumber, 0L).second;

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("marksTable", marksTable);
        model.addAttribute("subjectsTitle", subjectsTitle);
        model.addAttribute("subjectsCount", table.size());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("lengthOfMonth", lengthOfMonth);
        model.addAttribute("pupilNumber", pupilNumber);
        model.addAttribute("pupil", pupils[pupilNumber - 1]);
       model.addAttribute("daysOfWeek", DiaryUtil.getDaysOfWeek(year, month, lengthOfMonth));

        return "pupil";
    }
}
