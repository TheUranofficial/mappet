package com.theuran.mappet.api.scripts.logger;

import com.caoccao.javet.exceptions.JavetException;
import com.ibm.icu.impl.CalendarUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class LoggerManager {
    private final LinkedList<Log> logs = new LinkedList<>();

    public void addLog(LogType type, Date date, String source, String message) {
        this.logs.add(new Log(type, date.getHours()+":"+date.getMinutes()+":"+date.getSeconds(), source, message));
    }

    public void addLog(LogType type, String source, String message) {
        this.addLog(type, new Date(), source, message);
    }

    public void addLog(LogType type, String source, JavetException javetException) {
        this.addLog(type, new Date(), source, javetException.getLocalizedMessage());
    }

    public LinkedList<Log> getLogs() {
        return this.logs;
    }

    public String getLogLabels() {
        StringBuilder label = new StringBuilder();

        for (Log log : this.logs) {
            label.append("[").append(log.getTime()).append("] ").append("(").append(log.getSource()).append(") ").append(log.getMessage()).append("\n");
        }

        return label.toString();
    }
}