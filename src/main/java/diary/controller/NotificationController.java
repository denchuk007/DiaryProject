package diary.controller;

import diary.model.Notification;
import diary.service.NotificationService;
import diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/remove/notification/{id}", method = RequestMethod.GET)
    public String deleteNotification(@PathVariable("id") Long id) {

        notificationService.delete(notificationService.findById(id));

        return "redirect:/parent";
    }

    @RequestMapping(value = "/remove/notifications/{id}", method = RequestMethod.GET)
    public String deleteNotifications(@PathVariable("id") Long id) {

        for (Notification notification : userService.findById(id).getNotifications()) {
            notificationService.delete(notification);
        }

        return "redirect:/parent";
    }
}
