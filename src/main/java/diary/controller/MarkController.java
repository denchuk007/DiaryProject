package diary.controller;

import diary.model.Mark;
import diary.model.Notification;
import diary.model.Subject;
import diary.model.User;
import diary.service.*;
import diary.validator.MarkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MarkController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private MarkService markService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MarkValidator markValidator;

    @RequestMapping(value = "/new-mark/{id}", method = RequestMethod.GET)
    public String newMark(@PathVariable("id") Long pupilId, Model model) {

        model.addAttribute("mark", new Mark());
        model.addAttribute("pupil", userService.findById(pupilId));
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("subjects", subjectService.findAll());

        return "new-mark";
    }

    @RequestMapping(value = "/new-mark/{id}", method = RequestMethod.POST)
    public String newMark(@ModelAttribute("markForm") Mark mark,
                          @PathVariable("id") Long pupilId,
                          @RequestParam("subjectId") Long subjectId,
                          BindingResult bindingResult, Model model) {

        mark.setTeacher(securityService.findLoggedInUser());
        mark.setPupil(userService.findById(pupilId));
        mark.setSubject(subjectService.findById(subjectId));

        markService.save(mark);

        for (User parent : mark.getPupil().getParents()) {
            Notification notification = new Notification();
            notification.setText(mark.getDate() + " | Ученик " + mark.getPupil().getName() + " " + mark.getPupil().getSurname() +
                    " получил оценку " + mark.getValue() + " по предмету " + mark.getSubject().getTitle() + ".");

            notification.setParent(parent);

            notificationService.save(notification);
        }

        return "redirect:/teacher/" + pupilId;
    }

    @RequestMapping(value = "/edit/mark/{pupilId}/{markId}", method = RequestMethod.GET)
    public String editMark(@PathVariable("pupilId") Long pupilId,
                           @PathVariable("markId") Long markId,
                           Model model) {

        Mark currentMark = markService.findById(markId);
        model.addAttribute("mark", currentMark);
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("subjects", subjectService.findAll());

        return "new-mark";
    }

    @RequestMapping(value = "/edit/mark/{pupilId}/{markId}", method = RequestMethod.POST)
    public String editMark(@ModelAttribute("mark") Mark mark,
                              @PathVariable("pupilId") Long pupilId,
                              @PathVariable("markId") Long markId,
                              @RequestParam("subjectId") Long subjectId,
                              BindingResult bindingResult, Model model) {

        model.addAttribute("subjects", subjectService.findAll());

        markValidator.validate(mark, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-mark";
        }

        mark.setTeacher(securityService.findLoggedInUser());
        mark.setPupil(userService.findById(pupilId));
        mark.setSubject(subjectService.findById(subjectId));

        markService.save(mark);

        return "redirect:/new-mark";
    }

    @RequestMapping(value = "/remove/mark/{id}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("id") Long id) {

        markService.delete(id);

        return "redirect:/new-mark";
    }
}
