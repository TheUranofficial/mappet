package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueFloat;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundTrigger extends Trigger {
    public ValueString sound = new ValueString("sound", "");
    public ValueFloat volume = new ValueFloat("volume", 0.0f);
    public ValueFloat pitch = new ValueFloat("pitch", 0.0f);

    public SoundTrigger() {
        this.add(this.sound);
        this.add(this.volume);
        this.add(this.pitch);
    }

    public SoundTrigger(String sound, float volume, float pitch) {
        this();
        this.sound.set(sound);
        this.volume.set(volume);
        this.pitch.set(pitch);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        scriptEvent.getSubject().getMinecraftEntity().playSound(SoundEvent.of(new Identifier(this.sound.get())), this.volume.get(), this.pitch.get());
    }

    @Override
    public String getTriggerId() {
        return "sound";
    }
}
