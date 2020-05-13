package diary.service;

import diary.dao.NotificationDao;
import diary.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public void save(Notification notification) {
        notification.setText(notification.getText());
        notification.setParent(notification.getParent());
        notificationDao.save(notification);
    }

    @Override
    public void delete(Notification notification) {
        notificationDao.delete(notification);
    }

    @Override
    public Notification findById(Long id) {
        return notificationDao.findOne(id);
    }

    @Override
    public List<Notification> findAll() {
        return notificationDao.findAll();
    }
}
