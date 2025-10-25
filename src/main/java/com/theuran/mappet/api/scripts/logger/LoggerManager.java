package com.theuran.mappet.api.scripts.logger;

import java.util.LinkedList;

public class LoggerManager {
    private final LinkedList<Log> logs = new LinkedList<>();

    public void addLog(LogType type, String time, String source, String message) {
        this.logs.add(new Log(type, time, source, message));
    }

    public LinkedList<Log> getLogs() {
        return this.logs;
    }

    public String getLogLabels() {
        String label = "";

        for (Log log : this.logs) {
            label += "["+log.time+"] "+"("+log.source+") "+log.message;
        }

        return label;
    }
}