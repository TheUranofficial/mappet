package com.theuran.mappet.api.scripts.logger;

public class Log {
    private LogType type;
    private String time;
    private String source;
    private String message;

    public Log(LogType type, String time, String source, String message) {
        this.type = type;
        this.time = time;
        this.source = source;
        this.message = message;
    }

    public LogType getType() {
        return this.type;
    }

    public String getTime() {
        return this.time;
    }

    public String getSource() {
        return this.source;
    }

    public String getMessage() {
        return this.message;
    }
}
