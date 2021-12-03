package id.zakariaandy.demo.configuration;
import id.zakariaandy.demo.enums.LogLevelEnum;

import java.util.HashMap;

public class LogConfiguration {

    private int frequency;
    private int duration;
    private int waitTime;

    private static HashMap<String, LogConfiguration> CONFIGS = new HashMap<>();

    public LogConfiguration(int frequency, int duration, int waitTime) {
        this.frequency = frequency;
        this.duration = duration;
        this.waitTime = waitTime;
    }

    static {
        CONFIGS.put(LogLevelEnum.BLOCKER.getCode(), new LogConfiguration(1,5,1));
        CONFIGS.put(LogLevelEnum.CRITICAL.getCode(), new LogConfiguration(2,7,1));
        CONFIGS.put(LogLevelEnum.WARN.getCode(), new LogConfiguration(3,9,1));
        CONFIGS.put(LogLevelEnum.INFO.getCode(), new LogConfiguration(4,11,1));
    }

    public static HashMap<String, LogConfiguration> loadConfig() {
        return CONFIGS;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getDuration() {
        return duration;
    }

    public int getWaitTime() {
        return waitTime;
    }
}
