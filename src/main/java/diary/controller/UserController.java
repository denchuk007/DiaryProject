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

//    @RequestMapping(value = "/pupil/{id}", method = RequestMethod.GET)
//    public String pupil(@PathVariable("id") Long id, Model model) {
//        User currentUser = securityService.findLoggedInUser();
//        List<Mark> marks = new ArrayList<>();
//        for (Mark mark : currentUser.getMarks()) {
//            if (mark.getSubject().getId().equals(id)) {
//                marks.add(mark);
//            }
//        }
//        model.addAttribute("currentUserMarks", marks);
//        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
//        model.addAttribute("userForm", new User());
//        model.addAttribute("teachers", userService.findAllByRole(3L));
//        model.addAttribute("subjects", subjectService.findAll());
//
//        return "pupil";
//    }

    @RequestMapping(value = "/pupil/{month}/{year}", method = RequestMethod.GET)
    public String pupil(@PathVariable("month") int month,
                        @PathVariable("year") int year, Model model) {
        User currentUser = securityService.findLoggedInUser();
        List<Mark> marks = new ArrayList<>();
        Map<String, List<Mark>> table = new HashMap<>();
        int lengthOfMonth = LocalDate.of(year, month, 1).lengthOfMonth();

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

        String[][] finalTable = new String[table.size()][32];
        Iterator<String> iterator = table.keySet().iterator();
        for (int i = 0; i < table.size(); i++) {
            finalTable[i][0] = iterator.next();
            for (int j = 1; j <= lengthOfMonth; j++) {
                Calendar calendar = Calendar.getInstance();
                if (!table.get(finalTable[i][0]).isEmpty()) {
                    for (Mark mark : table.get(finalTable[i][0])) {
                        calendar.setTime(mark.getDate());

                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        if (day == j) {
                            finalTable[i][j] = mark.getValue().toString();
                            table.get(finalTable[i][0]).remove(mark);
                            break;
                        }
                        //finalTable[i][j] = "-";
                    }
                }
            }
        }

        model.addAttribute("currentUser", securityService.findLoggedInUsername());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("marksTable", finalTable);
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
}
