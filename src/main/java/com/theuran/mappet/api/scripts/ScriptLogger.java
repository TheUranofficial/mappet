package com.theuran.mappet.api.scripts;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.logger.LogType;

public class ScriptLogger {
    public void info(String message) {
        Mappet.getLogger().addLog(LogType.INFO, "", message);
    }

    public void info(String source, String message) {
        Mappet.getLogger().addLog(LogType.INFO, source, message);
    }

    public void error(String message) {
        Mappet.getLogger().addLog(LogType.ERROR, "", message);
    }

    public void error(String source, String message) {
        Mappet.getLogger().addLog(LogType.ERROR, source, message);
    }

    public void warn(String message) {
        Mappet.getLogger().addLog(LogType.WARN, "", message);
    }

    public void warn(String source, String message) {
        Mappet.getLogger().addLog(LogType.WARN, source, message);
    }
}
