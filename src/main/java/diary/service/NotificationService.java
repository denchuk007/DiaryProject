package diary.service;

import diary.model.Notification;

import java.util.List;

public interface NotificationService {

    void save(Notification notification);
    void delete(Notification notification);
    Notification findById(Long id);
    List<Notification> findAll();
}
