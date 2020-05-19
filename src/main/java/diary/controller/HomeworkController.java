package diary.controller;

import diary.model.Classroom;
import diary.model.Homework;
import diary.service.ClassroomService;
import diary.service.HomeworkService;
import diary.service.SecurityService;
import diary.service.SubjectService;
import diary.validator.HomeworkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private HomeworkValidator homeworkValidator;

    @RequestMapping(value = "/new-homework/{id}", method = RequestMethod.GET)
    public String createHomework(@PathVariable("id") Long id,  Model model) {
        model.addAttribute("homeworkForm", new Homework());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("currentClassroom", classroomService.findById(id));
        model.addAttribute("subjects", subjectService.findAll());

        return "new-homework";
    }

    @RequestMapping(value = "/new-homework/{id}", method = RequestMethod.POST)
    public String createHomework(@ModelAttribute("homeworkForm") Homework homework,
                                 @PathVariable("id") Long id,
                                 @RequestParam("subjectId") Long subjectId, BindingResult bindingResult, Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        homework.setSubject(subjectService.findById(subjectId));
        homework.setClassroom(classroomService.findById(id));

        homeworkValidator.validate(homework, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-homework";
        }

        homeworkService.save(homework);

        return "redirect:/classrooms";
    }

    @RequestMapping(value = "/homework/{id}", method = RequestMethod.GET)
    public String homework(@PathVariable("id") Long id,  Model model) {
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("currentClassroom", classroomService.findById(id));

        return "homework";
    }

    @RequestMapping(value = "/homework/{id}/{date}", method = RequestMethod.GET)
    public String homework(@PathVariable("id") Long id,
                           @PathVariable("date") Date date, Model model) {

        Classroom currentClassroom = classroomService.findById(id);

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("currentClassroom", currentClassroom);
        model.addAttribute("currentClassroomHomework", homeworkService.findByClassroomAndDate(currentClassroom, date));

        return "homework";
    }

    @RequestMapping(value = "/edit/homework/{classroomId}/{homeworkId}", method = RequestMethod.GET)
    public String editHomework(@PathVariable("classroomId") Long classroomId,
                               @PathVariable("homeworkId") Long homeworkId,
                               Model model) {

        Homework currentHomework = homeworkService.findById(homeworkId);

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());
        model.addAttribute("homeworkForm", currentHomework);
        model.addAttribute("subjects", subjectService.findAll());

        return "new-homework";
    }

    @RequestMapping(value = "/edit/homework/{classroomId}/{homeworkId}", method = RequestMethod.POST)
    public String editHomework(@ModelAttribute("homeworkForm") Homework homework,
                               @PathVariable("classroomId") Long classroomId,
                               @PathVariable("homeworkId") Long homeworkId,
                               @RequestParam("subjectId") Long subjectId, BindingResult bindingResult, Model model) {

        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        homework.setClassroom(classroomService.findById(classroomId));
        homework.setSubject(subjectService.findById(subjectId));

        homeworkValidator.validate(homework, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-homework";
        }

        homeworkService.save(homework);

        return "redirect:/classrooms";
    }

    @RequestMapping(value = "/delete/homework/{id}", method = RequestMethod.GET)
    public String deleteHomework(@PathVariable("id") Long id) {

        Homework currentHomework = homeworkService.findById(id);

        homeworkService.delete(currentHomework);

        return "redirect:/classrooms";
    }
}
