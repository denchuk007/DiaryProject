package diary.controller;

import diary.model.Subject;
import diary.service.SecurityService;
import diary.service.SubjectService;
import diary.validator.SubjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectValidator subjectValidator;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/new-subject", method = RequestMethod.GET)
    public String createSubject(Model model) {
        model.addAttribute("subjectForm", new Subject());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        return "new-subject";
    }

    @RequestMapping(value = "/new-subject", method = RequestMethod.POST)
    public String createSubject(@ModelAttribute("subjectForm") Subject subject, BindingResult bindingResult, Model model) {

        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        subjectValidator.validate(subject, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-subject";
        }

        subjectService.save(subject);

        return "redirect:/new-subject";
    }

    @RequestMapping(value = "/edit/subject/{subjectId}", method = RequestMethod.GET)
    public String editSubject(@PathVariable("subjectId") Long subjectId, Model model) {

        Subject currentSubject = subjectService.findById(subjectId);
        model.addAttribute("subjectForm", currentSubject);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        return "new-subject";
    }

    @RequestMapping(value = "/edit/subject/{subjectId}", method = RequestMethod.POST)
    public String editSubject(@ModelAttribute("subjectForm") Subject subject,
                              @PathVariable("subjectId") Long subjectId,
                              BindingResult bindingResult, Model model) {

        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("currentUser", securityService.findLoggedInUser());
        model.addAttribute("currentUserAuthorities", securityService.findLoggedInUsername().getAuthorities().iterator().next());

        subjectValidator.validate(subject, bindingResult);

        if (bindingResult.hasErrors()) {
            return "new-subject";
        }

        subjectService.save(subject);

        return "redirect:/new-subject";
    }

    @RequestMapping(value = "/remove/subject/{subjectId}", method = RequestMethod.GET)
    public String removeSubject(@PathVariable("subjectId") Long subjectId) {

        subjectService.delete(subjectId);

        return "redirect:/new-subject";
    }
}
