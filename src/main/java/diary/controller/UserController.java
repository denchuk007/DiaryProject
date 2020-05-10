package diary.controller;

import diary.model.Mark;
import diary.model.User;
import diary.service.*;
import diary.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MarkService markService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/parent", method = RequestMethod.GET)
    public String parent(Model model) {
        User currentUser =  securityService.findLoggedInUser();
        User[] pupils = new User[currentUser.getPupils().size()];
        Iterator<User> pupilIterator = currentUser.getPupils().iterator();
        for (int i = 0; i < pupils.length; i++) {
            pupils[i] = pupilIterator.next();
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("currentUserPupils", pupils);

        return "parent";
    }

    @RequestMapping(value = "/parent/{pupilNumber}", method = RequestMethod.GET)
    public String parent(@PathVariable("pupilNumber") int pupilNumber, Model model) {
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("pupilNumber", pupilNumber);

        return "pupil";
    }

    @RequestMapping(value = "/parent/{pupilNumber}/{month}/{year}", method = RequestMethod.GET)
    public String parent(@PathVariable("month") int month,
                        @PathVariable("year") int year,
                        @PathVariable("pupilNumber") int pupilNumber,
                        Model model) {

        Map<String, List<Mark>> table = new HashMap<>();
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();

        String[] subjectsTitle = getPupilMarks(month, year, table, lengthOfMonth, pupilNumber).first;
        Mark[][] marksTable = getPupilMarks(month, year, table, lengthOfMonth, pupilNumber).second;

        model.addAttribute("currentUser", securityService.findLoggedInUsername());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("marksTable", marksTable);
        model.addAttribute("subjectsTitle", subjectsTitle);
        model.addAttribute("subjectsCount", table.size());
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("lengthOfMonth", lengthOfMonth);
        model.addAttribute("pupilNumber", pupilNumber);

        return "pupil";
    }

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

        Map<String, List<Mark>> table = new HashMap<>();
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();

        String[] subjectsTitle = getPupilMarks(month, year, table, lengthOfMonth, 0).first;
        Mark[][] marksTable = getPupilMarks(month, year, table, lengthOfMonth, 0).second;

        model.addAttribute("currentUser", securityService.findLoggedInUsername());
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

    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public String teacher(Model model) {
        model.addAttribute("currentUser", securityService.findLoggedInUsername());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("pupils", userService.findAllCurrentTeacherPupils());

        return "teacher";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {

        return "error";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("userForm", user);
        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("pupils", userService.findAllByRole(1L));

        return "registration";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("userForm") User userForm,
                       @RequestParam(required = true, defaultValue = "") Long roleId,
                       @RequestParam(required = true, defaultValue = "") Long classroomId,
                       @RequestParam(required = true, defaultValue = "") Long pupilId,
                       BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            userService.save(userForm, roleId, classroomId, pupilId);
            if ((roleId == 1 && classroomId == 0)) {
                // userService.setPupilToTheClassroom(classroomId, userForm.getId());
            } else if (roleId == 2 && pupilId != 0) {
                //userService.setPupilToTheParent(userForm.getId(), pupilId);
            } else if (roleId == 3 && classroomId != 0) {
                //userService.setTeacherToTheClassroom(classroomId, userForm.getId());
            } else if (roleId != 4) {
                userService.deleteUser(userForm.getId());
                return "redirect:/error";
            }
        } else {
            userService.deleteUser(userForm.getId());
            return "redirect:/error";
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("pupils", userService.findAllByRole(1L));

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm,
                               @RequestParam(required = true, defaultValue = "") Long roleId,
                               @RequestParam(required = true, defaultValue = "") Long classroomId,
                               @RequestParam(required = true, defaultValue = "") Long pupilId,
                               BindingResult bindingResult, Model model) {

        userValidator.validate(userForm, bindingResult);

        if (!bindingResult.hasErrors()) {
            userService.save(userForm, roleId, classroomId, pupilId);
            if ((roleId == 1 && classroomId == 0)) {
                // userService.setPupilToTheClassroom(classroomId, userForm.getId());
            } else if (roleId == 2 && pupilId != 0) {
                //userService.setPupilToTheParent(userForm.getId(), pupilId);
            } else if (roleId == 3 && classroomId != 0) {
                //userService.setTeacherToTheClassroom(classroomId, userForm.getId());
            } else if (roleId != 4) {
                userService.deleteUser(userForm.getId());
                return "redirect:/error";
            }
        } else {
            userService.deleteUser(userForm.getId());
            return "redirect:/error";
        }

        //securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/admin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Логин или пароль некоррекнты.");
        }

        if (logout != null) {
            model.addAttribute("message", "Успешный выход из аккаунта.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("currentUser", securityService.findLoggedInUsername());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userService.findAllUsers());
        model.addAttribute("loggedUser", securityService.findLoggedInUsername());

        return "admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userService.findAllUsers());
        model.addAttribute("loggedUser", securityService.findLoggedInUsername());

        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }

        return "redirect:/admin";
    }


    public Pair<String[], Mark[][]> getPupilMarks(int month, int year, Map<String, List<Mark>> table, int lengthOfMonth, int pupilNumber) {
        User currentUser = securityService.findLoggedInUser();
        if (pupilNumber != 0) {
            Iterator<User> pupilIterator = currentUser.getPupils().iterator();
            for (int i = 0; i < pupilNumber - 1; i++) {
                pupilIterator.next();
            }
            currentUser = pupilIterator.next();
        }

        List<Mark> marks = new ArrayList<>();

        for (Mark mark : currentUser.getMarks()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mark.getDate());
            int m = calendar.get(Calendar.MONTH) + 1;
            int y = calendar.get(Calendar.YEAR);
            if (m == month && y == year) {
                marks.add(mark);
            }
        }

        for (Mark mark : marks) {
            String subjectTitle = mark.getSubject().getTitle();
            List<Mark> tableList;
            if (table.get(subjectTitle) != null) {
                tableList = table.get(subjectTitle);
            } else {
                tableList = new ArrayList<>();
            }
            tableList.add(mark);
            table.put(subjectTitle, tableList);
        }

        String[] subjectsTitle = new String[table.size()];
        Mark[][] marksTable = new Mark[table.size()][lengthOfMonth];
        Iterator<String> iterator = table.keySet().iterator();
        for (int i = 0; i < table.size(); i++) {
            subjectsTitle[i] = iterator.next();
            for (int j = 1; j <= lengthOfMonth; j++) {
                Calendar calendar = Calendar.getInstance();
                if (!table.get(subjectsTitle[i]).isEmpty()) {
                    for (Mark mark : table.get(subjectsTitle[i])) {
                        calendar.setTime(mark.getDate());

                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        if (day == j) {
                            marksTable[i][j] = mark;
                            table.get(subjectsTitle[i]).remove(mark);
                            break;
                        }
                    }
                }
            }
        }
        return new Pair(subjectsTitle, marksTable);
    }
}
