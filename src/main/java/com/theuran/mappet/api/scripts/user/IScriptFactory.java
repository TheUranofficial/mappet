package com.theuran.mappet.api.scripts.user;

import com.theuran.mappet.api.scripts.ScriptLogger;
import com.theuran.mappet.api.scripts.code.ScriptVector;

/**
* Factory interface
*/

public interface IScriptFactory {
    ScriptVector vector(double x, double y, double z);

    ScriptLogger logger();
}
