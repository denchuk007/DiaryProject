package diary.controller;

import diary.model.Classroom;
import diary.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @RequestMapping(value = "/create-classroom", method = RequestMethod.GET)
    public String createClass(Model model) {
        model.addAttribute("pupilClass", new Classroom());

        return "create-classroom";
    }

    @RequestMapping(value = "/create-classroom", method = RequestMethod.POST)
    public String createClass(@ModelAttribute("classroomForm") Classroom classroom, BindingResult bindingResult, Model model) {
        model.addAttribute("pupilClass", new Classroom());
        classroomService.save(classroom);

        return "create-classroom";
    }
}
