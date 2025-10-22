package com.theuran.mappet.api.scripts;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptServer;
import com.theuran.mappet.api.scripts.code.ScriptWorld;
import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class Properties {
    private final Map<String, Object> map = new HashMap<>();

    public static Properties create() {
        return new Properties();
    }

    public static Properties create(String script, String function, Entity subject, Entity object, World world, MinecraftServer server) {
        Properties properties = new Properties();

        properties.map.put("Context", new ScriptEvent(
                script,
                function,
                ScriptEntity.create(subject),
                ScriptEntity.create(object),
                world == null ? null : new ScriptWorld(world),
                server == null ? null : new ScriptServer(server)
        ));

        return properties;
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public Object get(String key) {
        return this.map.get(key);
    }

    public void put(String key, Object value) {
        this.map.put(key, value);
    }

    public Properties copy() {
        ScriptEvent scriptEvent = (ScriptEvent) this.map.get("Context");

        return Properties.create(
                scriptEvent.getScript(),
                scriptEvent.getFunction(),
                scriptEvent.getSubject() == null ? null : scriptEvent.getSubject().getMinecraftEntity(),
                scriptEvent.getObject() == null ? null : scriptEvent.getObject().getMinecraftEntity(),
                scriptEvent.getWorld() == null ? null : scriptEvent.getWorld().getMinecraftWorld(),
                scriptEvent.getServer() == null ? null : scriptEvent.getServer().getMinecraftServer()
        );
    }
}
