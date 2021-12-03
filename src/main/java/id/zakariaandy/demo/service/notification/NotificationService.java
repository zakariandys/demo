package id.zakariaandy.demo.service.notification;

import java.util.List;

public interface NotificationService {
    void pushNotification(List<String> channel, String logLevel);
}
