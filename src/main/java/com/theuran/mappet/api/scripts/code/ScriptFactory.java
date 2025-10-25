package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.api.scripts.ScriptLogger;

public class ScriptFactory {
    public ScriptVector vector(double x, double y, double z) {
        return new ScriptVector(x, y, z);
    }

    public ScriptLogger logger() {
        return new ScriptLogger();
    }
}
