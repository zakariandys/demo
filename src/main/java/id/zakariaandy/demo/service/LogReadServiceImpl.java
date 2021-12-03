package id.zakariaandy.demo.service;

import id.zakariaandy.demo.enums.LogLevelEnum;
import id.zakariaandy.demo.request.LogData;
import id.zakariaandy.demo.service.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogReadServiceImpl implements LogReadService {

    private Map<String, LogHandler> logHandlerStrategy = new HashMap<>();

    @Autowired
    private BlockerLogHandler blockerLogHandler;
    @Autowired
    private CriticalLogHandler criticalLogHandler;
    @Autowired
    private WarnLogHandler warnLogHandler;
    @Autowired
    private InfoLogHandler infoLogHandler;

    public LogReadServiceImpl() {
        this.logHandlerStrategy.put(LogLevelEnum.BLOCKER.getCode(), blockerLogHandler);
        this.logHandlerStrategy.put(LogLevelEnum.CRITICAL.getCode(), criticalLogHandler);
        this.logHandlerStrategy.put(LogLevelEnum.WARN.getCode(), warnLogHandler);
        this.logHandlerStrategy.put(LogLevelEnum.INFO.getCode(), infoLogHandler);
    }

    @Override
    public void readAndNotify(List<LogData> logDatas) throws InterruptedException {
        for (LogData logData : logDatas) {
            switch (logData.getLogLevel().getCode()) {
                case "BLOCKER":
                    blockerLogHandler.processLogData(logData);
                    break;
                case "CRITICAL":
                    criticalLogHandler.processLogData(logData);
                    break;
                case "WARN":
                    warnLogHandler.processLogData(logData);
                    break;
                case "INFO":
                    infoLogHandler.processLogData(logData);
                    break;
                default:
                    System.out.println("Unknown log level");
                    break;
            }
        }
    }
}
