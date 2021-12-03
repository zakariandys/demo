package id.zakariaandy.demo.request;

import id.zakariaandy.demo.enums.LogLevelEnum;

import java.util.Date;

public class LogData {

    private Date date;

    private LogLevelEnum logLevel;

    public LogData(Date date, LogLevelEnum logLevel) {
        this.date = date;
        this.logLevel = logLevel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LogLevelEnum getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevelEnum logLevel) {
        this.logLevel = logLevel;
    }
}
