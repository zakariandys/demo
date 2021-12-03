package id.zakariaandy.demo.service.notification;

import id.zakariaandy.demo.configuration.UserSubscriptionConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void pushNotification(List<String> channels, String logLevel) {
        UserSubscriptionConfiguration.loadConfig()
                .stream().filter(userData -> userData.getLogLevelSubscription().getCode().equals(logLevel))
                .forEach(userData -> System.out.println
                        ("Send push notification " + logLevel
                                + " through " + channels.stream().collect(Collectors.joining(","))
                                + " to userId:" + userData.getUserId()));
    }
}
