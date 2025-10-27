package com.theuran.mappet.api.scripts.code.mappet;

import com.theuran.mappet.api.states.States;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;

import java.util.Set;

public class MappetStates {
    public States states;

    public MappetStates(States states) {
        this.states = states;
    }
    
    public void setString(String id, String value) {
        this.states.setString(id, value);
    }

    public String getString(String id) {
        return this.states.getString(id);
    }

    public void setBoolean(String id, boolean value) {
        this.states.setBoolean(id, value);
    }

    public boolean getBoolean(String id) {
        return this.states.getBoolean(id);
    }

    public void setNumber(String id, double value) {
        this.states.setNumber(id, value);
    }

    public double getNumber(String id) {
        return this.states.getNumber(id);
    }


    public BaseType get(String key) {
        return this.states.get(key);
    }

    public Set<String> keys() {
        return this.states.keys();
    }

    public int size() {
        return this.states.size();
    }

    public boolean has(String key) {
        return this.states.has(key);
    }

    public void remove(String key) {
        this.states.remove(key);
    }
}
