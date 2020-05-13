package diary.dao;

import diary.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDao extends JpaRepository<Notification, Long> {


}
