package com.theuran.mappet.network.packets.keybinds;

import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.keybinds.UIKeybindsOverlayPanel;
import com.theuran.mappet.network.core.ClientPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import mchorse.bbs_mod.l10n.keys.IKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeybindsUpdatePacket extends ClientPacket {
    private Map<Keybind, Integer> keybinds;

    public KeybindsUpdatePacket() {
        super();
    }

    public KeybindsUpdatePacket(Map<Keybind, Integer> events) {
        this();
        this.keybinds = events;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeMap(this.keybinds, MappetByteBuffer::writeKeybind, PacketByteBuf::writeInt);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.keybinds = buf.readMap(packet -> MappetByteBuffer.readKeybind(buf), PacketByteBuf::readInt);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        UIKeybindsOverlayPanel panel = MappetClient.getDashboard().keybindsPanel;

        panel.keybinds.clear();

        for (Keybind keybind : this.keybinds.keySet()) {
            List<IKey> keys = new ArrayList<>();

            keys.add(IKey.raw(keybind.id()));

            if (this.keybinds.get(keybind) != 0) {
                keys.add(IKey.constant(" §7(§6" + this.keybinds.get(keybind) + "§7)§r"));
            }

            panel.keybinds.add(IKey.comp(keys), keybind.id());
        }

        panel.keybinds.sort();
        panel.keybinds.setCurrentValue(panel.latest);
    }
}
