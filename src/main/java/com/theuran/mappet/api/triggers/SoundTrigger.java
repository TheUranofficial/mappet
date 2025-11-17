package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.panels.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UISoundTriggerPanel;
import mchorse.bbs_mod.settings.values.numeric.ValueFloat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundTrigger extends StringTrigger {
    public ValueFloat volume = new ValueFloat("volume", 0.0f);
    public ValueFloat pitch = new ValueFloat("pitch", 0.0f);

    public SoundTrigger() {
        super();
        this.add(this.volume);
        this.add(this.pitch);
    }

    public SoundTrigger(String sound, float volume, float pitch) {
        this();
        this.key.set(sound);
        this.volume.set(volume);
        this.pitch.set(pitch);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        if (this.key.get().isEmpty())
            return;

        scriptEvent.getSubject().getMinecraftEntity().playSound(SoundEvent.of(new Identifier(this.key.get())), this.volume.get(), this.pitch.get());
    }

    @Override
    public void execute(ClientScriptEvent scriptEvent) {
        MinecraftClient.getInstance().player.playSound(SoundEvent.of(new Identifier(this.key.get())), this.volume.get(), this.pitch.get());
    }

    @Override
    public String getTriggerId() {
        return "sound";
    }

    @Environment(EnvType.CLIENT)
    @Override
    public UITriggerPanel<?> getPanel(UIEditorTriggersOverlayPanel overlayPanel) {
        return new UISoundTriggerPanel(overlayPanel, this);
    }
}
