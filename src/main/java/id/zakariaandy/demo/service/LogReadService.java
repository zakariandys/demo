package id.zakariaandy.demo.service;

import id.zakariaandy.demo.request.LogData;

import java.util.List;

public interface LogReadService {
    void readAndNotify(List<LogData> logData) throws InterruptedException;
}
