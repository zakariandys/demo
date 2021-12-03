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
public class WarnLogHandler implements LogHandler {

    @Autowired
    private NotificationService notificationService;

    private static Queue<LogData> WARN_COUNTER = new LinkedList<>();

    private static LogConfiguration LOG_CONFIG = LogConfiguration.loadConfig().get(LogLevelEnum.WARN.getCode());

    private static long COOLING_DOWN = 0;

    private static LogData timeFreeze = null;

    @Override
    public void processLogData(LogData logData) {
        long diff = logData.getDate().getTime() - (timeFreeze == null ? 0 : timeFreeze.getDate().getTime());
        long duration = TimeUnit.MILLISECONDS.toSeconds(diff);

        if (duration <= COOLING_DOWN) {
            return;
        }

        COOLING_DOWN = 0;
        WARN_COUNTER.add(logData);
        diff = logData.getDate().getTime() - WARN_COUNTER.peek().getDate().getTime();
        duration = TimeUnit.MILLISECONDS.toSeconds(diff);
        if (duration >= LOG_CONFIG.getDuration()) {
            WARN_COUNTER.poll();
        }


        if (WARN_COUNTER.size() >= LOG_CONFIG.getFrequency()) {
            // Send push notification
            notificationService.pushNotification(Arrays.asList(NotificationChannelEnum.SMS.getChannel()), LogLevelEnum.WARN.getCode());

            // Update cooling down time
            COOLING_DOWN = LOG_CONFIG.getWaitTime();
            timeFreeze = logData;
        }
    }
}
