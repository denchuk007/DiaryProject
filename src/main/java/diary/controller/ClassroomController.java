package diary.controller;

import diary.model.Classroom;
import diary.model.Subject;
import diary.service.ClassroomService;
import diary.service.SecurityService;
import diary.validator.ClassroomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.charset.Charset;

@Controller
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ClassroomValidator classroomValidator;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/new-classroom", method = RequestMethod.GET)
    public String createClass(Model model) {
        model.addAttribute("classroomForm", new Classroom());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        return "new-classroom";
    }

    @RequestMapping(value = "/new-classroom", method = RequestMethod.POST)
    public String createClass(@ModelAttribute("classroomForm") Classroom classroom, BindingResult bindingResult, Model model) {

        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        classroomValidator.validate(classroom, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-classroom";
        }

        classroomService.save(classroom);

        return "redirect:/new-classroom";
    }

    @RequestMapping(value = "/edit/classroom/{classroomId}", method = RequestMethod.GET)
    public String editSubject(@PathVariable("classroomId") Long classroomId, Model model) {

        Classroom currentClassroom = classroomService.findById(classroomId);
        model.addAttribute("classroomForm", currentClassroom);
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        return "new-classroom";
    }

    @RequestMapping(value = "/edit/classroom/{classroomId}", method = RequestMethod.POST)
    public String editSubject(@ModelAttribute("classroomForm") Classroom classroom,
                              @PathVariable("classroomId") Long classroomId,
                              BindingResult bindingResult, Model model) {

        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        classroomValidator.validate(classroom, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-classroom";
        }

        classroomService.save(classroom);

        return "redirect:/new-classroom";
    }

    @RequestMapping(value = "/remove/classroom/{classroomId}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("classroomId") Long classroomId) {

        classroomService.delete(classroomId);

        return "redirect:/new-classroom";
    }
}
