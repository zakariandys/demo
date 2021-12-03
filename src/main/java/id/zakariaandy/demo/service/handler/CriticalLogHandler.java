package id.zakariaandy.demo.service.handler;

import id.zakariaandy.demo.configuration.LogConfiguration;
import id.zakariaandy.demo.enums.LogLevelEnum;
import id.zakariaandy.demo.enums.NotificationChannelEnum;
import id.zakariaandy.demo.request.LogData;
import id.zakariaandy.demo.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Component
public class CriticalLogHandler implements LogHandler {

    private static Queue<LogData> CRITICAL = new LinkedList<>();

    private static LogConfiguration LOG_CONFIG = LogConfiguration.loadConfig().get(LogLevelEnum.CRITICAL.getCode());

    private static long COOLING_DOWN = 0;

    private static LogData timeFreeze = null;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void processLogData(LogData logData) {
        long diff = logData.getDate().getTime() - (timeFreeze == null ? 0 : timeFreeze.getDate().getTime());
        long duration = TimeUnit.MILLISECONDS.toSeconds(diff);

        if (duration <= COOLING_DOWN) {
            return;
        }

        COOLING_DOWN = 0;
        CRITICAL.add(logData);
        diff = logData.getDate().getTime() - CRITICAL.peek().getDate().getTime();
        duration = TimeUnit.MILLISECONDS.toSeconds(diff);
        if (duration >= LOG_CONFIG.getDuration()) {
            CRITICAL.poll();
        }


        if (CRITICAL.size() >= LOG_CONFIG.getFrequency()) {
            // Send push notification
            notificationService.pushNotification(Arrays.asList(NotificationChannelEnum.SMS.getChannel(),
                            NotificationChannelEnum.EMAIL.getChannel(),
                            NotificationChannelEnum.PHONE.getChannel()),
                    LogLevelEnum.CRITICAL.getCode());

            // Update cooling down time
            COOLING_DOWN = LOG_CONFIG.getWaitTime();
            timeFreeze = logData;
        }
    }
}
