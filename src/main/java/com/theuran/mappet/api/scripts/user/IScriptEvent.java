package com.theuran.mappet.api.scripts.user;

import com.theuran.mappet.api.scripts.code.ScriptServer;
import com.theuran.mappet.api.scripts.code.ScriptWorld;
import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;

/**
* Event interface
*/

public interface IScriptEvent {
    String getScript();

    ScriptEntity getSubject();

    ScriptEntity getObject();

    ScriptWorld getWorld();

    ScriptServer getServer();

    void set(String key, Object value);

    Object get(String key);

    void send(String message);
}
