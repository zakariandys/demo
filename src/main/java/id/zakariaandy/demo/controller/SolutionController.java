package id.zakariaandy.demo.controller;

import id.zakariaandy.demo.enums.LogLevelEnum;
import id.zakariaandy.demo.request.LogData;
import id.zakariaandy.demo.service.LogReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SolutionController {

    @Autowired
    private LogReadService logReadService;

    @GetMapping(path = "/log")
    public void checkAndNotifyUser() throws InterruptedException {

        // Request Data
        List<LogData> logDataList = new ArrayList<>();
        logDataList.add(new LogData(new Date(2021,12,02,18,00,01), LogLevelEnum.WARN));
        logDataList.add(new LogData(new Date(2021,12,02,18,00,02), LogLevelEnum.WARN));
        logDataList.add(new LogData(new Date(2021,12,02,18,00,03), LogLevelEnum.WARN));
        logDataList.add(new LogData(new Date(2021,12,02,18,00,04), LogLevelEnum.WARN));
        logDataList.add(new LogData(new Date(2021,12,02,18,00,04), LogLevelEnum.CRITICAL));
        logDataList.add(new LogData(new Date(2021,12,02,18,00,05), LogLevelEnum.CRITICAL));

        logReadService.readAndNotify(logDataList);
    }

}
