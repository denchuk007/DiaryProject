package diary.controller;

import diary.model.Mark;
import diary.service.MarkService;
import diary.service.SecurityService;
import diary.service.SubjectService;
import diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class MarkController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private MarkService markService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/new-mark/{id}", method = RequestMethod.GET)
    public String newMark(@PathVariable("id") String pupilId, Model model) {
        model.addAttribute("mark", new Mark());
        model.addAttribute("pupil", userService.findById(Long.valueOf(pupilId)));
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("subjects", subjectService.findAll());

        return "new-mark";
    }

    @RequestMapping(value = "/new-mark/{id}", method = RequestMethod.POST)
    public String newMark(@ModelAttribute("markForm") Mark mark,
                          @PathVariable("id") String pupilId,
                          @RequestParam("subjectId") String subjectId,
                          BindingResult bindingResult, Model model) {
        //mark.setDate(Date.valueOf(LocalDate.now()));
        mark.setTeacher(securityService.findLoggedInUser());
        mark.setPupil(userService.findById(Long.valueOf(pupilId)));
        mark.setSubject(subjectService.findById(Long.valueOf(subjectId)));
        markService.save(mark);
        return "redirect:/teacher/" + pupilId;
    }
}
