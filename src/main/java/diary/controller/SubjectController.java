package diary.controller;

import diary.model.Classroom;
import diary.model.Subject;
import diary.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/new-subject", method = RequestMethod.GET)
    public String createClass(Model model) {
        model.addAttribute("subjectForm", new Subject());

        return "new-subject";
    }

    @RequestMapping(value = "/new-subject", method = RequestMethod.POST)
    public String createClass(@ModelAttribute("subjectForm") Subject subject, BindingResult bindingResult, Model model) {
        subjectService.save(subject);

        return "redirect:/welcome";
    }
}
