package diary.controller;

import diary.dao.RoleDao;
import diary.model.News;
import diary.model.Role;
import diary.model.User;
import diary.service.ClassroomService;
import diary.service.NewsService;
import diary.service.SecurityService;
import diary.service.UserService;
import diary.util.DiaryUtil;
import diary.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Controller
public class UserController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {

        return "error";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        user.setPassword("");
        user.setMarks(user.getMarks());
        model.addAttribute("userForm", user);
        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("pupils", userService.findAllByRole(1L));
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        return "registration";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("userForm") User userForm,
                       @RequestParam(required = true, defaultValue = "") Long roleId,
                       @RequestParam(required = true, defaultValue = "") Long classroomId,
                       @RequestParam(required = true, defaultValue = "") String pupilsId,
                       BindingResult bindingResult, Model model) {

        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        getUserForm(userForm, classroomId, roleId, pupilsId);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userService.save(userForm);

        return "redirect:/admin";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("pupils", userService.findAllByRole(1L));
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        return "registration";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String registration(@ModelAttribute("userForm") User userForm,
                               @RequestParam(required = true, defaultValue = "") Long roleId,
                               @RequestParam(required = true, defaultValue = "") Long classroomId,
                               @RequestParam(required = true, defaultValue = "") String pupilsId,
                               BindingResult bindingResult, Model model) {

        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("pupils", userService.findAllByRole(1L));
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        getUserForm(userForm, classroomId, roleId, pupilsId);

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userForm.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userService.save(userForm);

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
        User currentUser = securityService.findLoggedInUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("birthdays", DiaryUtil.getBirthdays(currentUser, userService.findAllUsers()));
        model.addAttribute("news", newsService.findAll());
        model.addAttribute("newsForm", new News());

        return "welcome";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.POST)
    public String addPost(@ModelAttribute("newsForm") News newsForm, Model model) {
        User currentUser = securityService.findLoggedInUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("birthdays", DiaryUtil.getBirthdays(currentUser, userService.findAllUsers()));
        model.addAttribute("news", newsService.findAll());

        newsForm.setDate(new Date(new java.util.Date().getTime()));

        newsService.save(newsForm);

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/edit/post/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") Long id, Model model) {

        User currentUser = securityService.findLoggedInUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("birthdays", DiaryUtil.getBirthdays(currentUser, userService.findAllUsers()));
        model.addAttribute("news", newsService.findAll());
        model.addAttribute("newsForm", newsService.findById(id));

        return "welcome";
    }

    @RequestMapping(value = "/edit/post/{id}", method = RequestMethod.POST)
    public String editPost(@ModelAttribute("newsForm") News newsForm,
                           @PathVariable("id") Long id) {

        newsService.save(newsForm);

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/remove/post/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") Long id) {

        newsService.delete(id);

        return "redirect:/welcome";
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

    private User getUserForm(User userForm, Long classroomId, Long roleId, String pupilsId) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(roleId));
        userForm.setRoles(roles);

        if (classroomId != 0 && pupilsId.equals("0")) {
            userForm.setClassroom(classroomService.findById(classroomId));
        }

        if (!pupilsId.equals("0")) {
            Set<User> pupils = new HashSet<>();
            String[] pupilsIdArray = pupilsId.split(",");
            for (String id : pupilsIdArray) {
                Long pupilId = Long.valueOf(id);
                pupils.add(userService.findById(pupilId));
            }
            userForm.setPupils(pupils);
        }

        return userForm;
    }
}
