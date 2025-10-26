package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundTrigger extends Trigger {
    private String sound;
    private float volume;
    private float pitch;

    public SoundTrigger(String sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        scriptEvent.getSubject().getMinecraftEntity().playSound(SoundEvent.of(new Identifier(this.sound)), this.volume, this.pitch);
    }

    @Override
    public String getId() {
        return "Sound";
    }

    @Override
    public void toData(MapType mapType) {
        mapType.putString("sound", this.sound);
        mapType.putFloat("volume", this.volume);
        mapType.putFloat("pitch", this.pitch);
    }

    @Override
    public void fromData(MapType entries) {
        this.sound = entries.getString("sound");
        this.volume = entries.getFloat("volume");
        this.pitch = entries.getFloat("pitch");
    }
}
