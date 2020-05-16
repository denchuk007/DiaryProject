package diary.controller;

import diary.model.Mark;
import diary.model.Notification;
import diary.model.User;
import diary.service.*;
import diary.validator.MarkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        model.addAttribute("markForm", new Mark());
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

        model.addAttribute("pupil", userService.findById(pupilId));
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("subjects", subjectService.findAll());

        mark.setTeacher(securityService.findLoggedInUser());
        mark.setPupil(userService.findById(pupilId));
        mark.setSubject(subjectService.findById(subjectId));


        markValidator.validate(mark, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-mark";
        }

        sendNotification(mark, null, false);
        markService.save(mark);

        return "redirect:/teacher/" + pupilId;
    }

    @RequestMapping(value = "/edit/mark/{pupilId}/{markId}", method = RequestMethod.GET)
    public String editMark(@PathVariable("pupilId") Long pupilId,
                           @PathVariable("markId") Long markId,
                           Model model) {

        Mark currentMark = markService.findById(markId);
        model.addAttribute("markForm", currentMark);
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("pupil", userService.findById(pupilId));

        return "new-mark";
    }

    @RequestMapping(value = "/edit/mark/{pupilId}/{markId}", method = RequestMethod.POST)
    public String editMark(@ModelAttribute("markForm") Mark mark,
                              @PathVariable("pupilId") Long pupilId,
                              @PathVariable("markId") Long markId,
                              @RequestParam("subjectId") Long subjectId,
                              BindingResult bindingResult, Model model) {

        Mark currentMark = markService.findById(markId);

        model.addAttribute("pupil", userService.findById(pupilId));
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("subjects", subjectService.findAll());

        mark.setTeacher(securityService.findLoggedInUser());
        mark.setPupil(userService.findById(pupilId));
        mark.setSubject(subjectService.findById(subjectId));

        markValidator.validate(mark, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-mark";
        }

        sendNotification(mark, currentMark, false);
        markService.save(mark);

        return "redirect:/teacher/" + pupilId;
    }

    @RequestMapping(value = "/remove/mark/{pupilId}/{markId}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("pupilId") Long pupilId,
                                @PathVariable("markId") Long markId) {

        Mark currentMark = markService.findById(markId);

        markService.delete(markId);
        sendNotification(currentMark, null, true);

        return "redirect:/teacher/" + pupilId;
    }

    private void sendNotification(Mark mark, Mark currentMark, boolean isDelete) {
        for (User parent : mark.getPupil().getParents()) {
            Notification notification = new Notification();
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedString = localDate.format(formatter);
            if (isDelete) {
                notification.setText(formattedString + " | " + mark.getPupil().getName() + " " + mark.getPupil().getSurname() +
                        " (" + mark.getPupil().getClassroom().getDigit() + mark.getPupil().getClassroom().getWord() +
                        " класс) получил удаление оценки " + mark.getValue() + " по предмету " + mark.getSubject().getTitle() + " за " + mark.getDate().toString() + "." +
                        " Учитель: " + mark.getTeacher().getName() + " " + mark.getTeacher().getSurname() + ".");
            } else if (currentMark == null) {
                notification.setText(formattedString + " | " + mark.getPupil().getName() + " " + mark.getPupil().getSurname() +
                        " (" + mark.getPupil().getClassroom().getDigit() + mark.getPupil().getClassroom().getWord() +
                        " класс) получил оценку " + mark.getValue() + " по предмету " + mark.getSubject().getTitle() + " за " + mark.getDate().toString() + "." +
                        " Учитель: " + mark.getTeacher().getName() + " " + mark.getTeacher().getSurname() + ".");
            } else {
                notification.setText(formattedString + " | " + mark.getPupil().getName() + " " + mark.getPupil().getSurname() +
                        " (" + mark.getPupil().getClassroom().getDigit() + mark.getPupil().getClassroom().getWord() +
                        " класс) получил изменение оценки с " + currentMark.getValue() + " (" + currentMark.getSubject().getTitle() + ")"
                        + " на " + mark.getValue() + " (" + mark.getSubject().getTitle() + ") за " + mark.getDate().toString() + "." +
                        " Учитель: " + mark.getTeacher().getName() + " " + mark.getTeacher().getSurname() + ".");
            }
            notification.setParent(parent);
            notificationService.save(notification);
        }
    }
}
