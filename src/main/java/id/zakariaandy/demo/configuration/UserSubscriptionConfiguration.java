package id.zakariaandy.demo.configuration;

import id.zakariaandy.demo.enums.LogLevelEnum;

import java.util.ArrayList;
import java.util.List;

public class UserSubscriptionConfiguration {
    private String userId;
    private LogLevelEnum logLevelSubscription;

    private static List<UserSubscriptionConfiguration> CONFIGS = new ArrayList<>();

    public UserSubscriptionConfiguration(String userId, LogLevelEnum logLevelSubscription) {
        this.userId = userId;
        this.logLevelSubscription = logLevelSubscription;
    }

    static {
        CONFIGS.add(new UserSubscriptionConfiguration("123", LogLevelEnum.CRITICAL));
        CONFIGS.add(new UserSubscriptionConfiguration("456", LogLevelEnum.CRITICAL));
        CONFIGS.add(new UserSubscriptionConfiguration("789", LogLevelEnum.WARN));
    }

    public static List<UserSubscriptionConfiguration> loadConfig() {
        return CONFIGS;
    }

    public String getUserId() {
        return userId;
    }

    public LogLevelEnum getLogLevelSubscription() {
        return logLevelSubscription;
    }
}
