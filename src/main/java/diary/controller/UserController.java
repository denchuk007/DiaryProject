package diary.controller;

import diary.service.ClassroomService;
import diary.service.SecurityService;
import diary.service.UserService;
import diary.validator.UserValidator;
import diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public String teacher(Model model) {
        model.addAttribute("currentUser", securityService.findLoggedInUsername());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("pupils", userService.findAllCurrentTeacherPupils());

        return "teacher";
    }

    @RequestMapping(value = "/pupil", method = RequestMethod.GET)
    public String pupil(Model model) {
        model.addAttribute("currentUser", securityService.findLoggedInUsername());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("userForm", new User());
        model.addAttribute("marks", userService.findAllCurrentPupilMarks());
        model.addAttribute("teachers", userService.findAllByRole(3L));

        return "pupil";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {

        return "error";
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
                               @RequestParam(required = true, defaultValue = "" ) Long roleId,
                               @RequestParam(required = true, defaultValue = "" ) Long classroomId,
                               @RequestParam(required = true, defaultValue = "" ) Long pupilId,
                               BindingResult bindingResult, Model model) {

        userValidator.validate(userForm, bindingResult);

        if (!bindingResult.hasErrors()) {
            userService.save(userForm, roleId);
            if (roleId == 1 && classroomId != 0) {
                userService.setPupilToTheClassroom(classroomId, userForm.getId());
            } else if (roleId == 2 && pupilId != 0) {
                userService.setPupilToTheParent(userForm.getId(), pupilId);
            } else if (roleId == 3 && classroomId != 0) {
                userService.setTeacherToTheClassroom(classroomId, userForm.getId());
            } else if (roleId != 4) {
                userService.deleteUser(userForm.getId());
                return "redirect:/error";
            }
        } else {
            userService.deleteUser(userForm.getId());
            return "redirect:/error";
        }

        //securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
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
    public String deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                             @RequestParam(required = true, defaultValue = "" ) String action, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", userService.findAllUsers());
        model.addAttribute("loggedUser", securityService.findLoggedInUsername());

        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }

        return "redirect:/admin";
    }
}
