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
public class InfoLogHandler implements LogHandler {

    private static Queue<LogData> INFO = new LinkedList<>();

    private static LogConfiguration LOG_CONFIG = LogConfiguration.loadConfig().get(LogLevelEnum.INFO.getCode());

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
        INFO.add(logData);
        diff = logData.getDate().getTime() - INFO.peek().getDate().getTime();
        duration = TimeUnit.MILLISECONDS.toSeconds(diff);
        if (duration >= LOG_CONFIG.getDuration()) {
            INFO.poll();
        }


        if (INFO.size() >= LOG_CONFIG.getFrequency()) {
            // Send push notification
            notificationService.pushNotification(Arrays.asList(NotificationChannelEnum.SMS.getChannel()),
                    LogLevelEnum.WARN.getCode());

            // Update cooling down time
            COOLING_DOWN = LOG_CONFIG.getWaitTime();
            timeFreeze = logData;
        }
    }
}
