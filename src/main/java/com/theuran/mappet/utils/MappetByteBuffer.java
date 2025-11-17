package com.theuran.mappet.utils;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MappetByteBuffer {
    public static void writeScript(PacketByteBuf buf, Script script) {
        buf.writeString(script.getId());
        buf.writeString(script.getContent());
        buf.writeBoolean(script.isServer());
    }

    public static Script readScript(PacketByteBuf buf) {
        String id = buf.readString();
        String content = buf.readString();
        boolean server = buf.readBoolean();

        Script script = new Script();

        script.setId(id);
        script.setContent(content);
        script.setServer(server);

        return script;
    }

    public static void writeScriptEvent(PacketByteBuf buf, ScriptEvent scriptEvent) {
        buf.writeString(scriptEvent.getScript());
        buf.writeString(scriptEvent.getFunction());

        int subjectId = -1;
        if (scriptEvent.subject != null) {
            subjectId = scriptEvent.subject.getMinecraftEntity().getId();
        }

        int objectId = -1;
        if (scriptEvent.object != null) {
            subjectId = scriptEvent.object.getMinecraftEntity().getId();
        }

        buf.writeInt(subjectId);
        buf.writeInt(objectId);

        buf.writeMap(scriptEvent.getValues(), PacketByteBuf::writeString, MappetByteBuffer::writeValue);
    }

    public static ScriptEvent readScriptEvent(PacketByteBuf buf) {
        String script = buf.readString();
        String function = buf.readString();

        int subjectId = buf.readInt();
        int objectId = buf.readInt();

        Map<String, Object> values = buf.readMap(PacketByteBuf::readString, MappetByteBuffer::readValue);

        values.put("__subjectId", subjectId);
        values.put("__objectId", objectId);

        ScriptEvent scriptEvent = ScriptEvent.create(
                script,
                function,
                null,
                null,
                null,
                null
        );

        scriptEvent.setValues(values);

        return scriptEvent;
    }

    public static void writeClientScriptEvent(PacketByteBuf buf, ClientScriptEvent clientScriptEvent) {
        buf.writeString(clientScriptEvent.getScript());
        buf.writeString(clientScriptEvent.getFunction());

        int subjectId = -1;
        if (clientScriptEvent.subject != null) {
            subjectId = clientScriptEvent.subject.getMinecraftEntity().getId();
        }

        int objectId = -1;
        if (clientScriptEvent.object != null) {
            subjectId = clientScriptEvent.object.getMinecraftEntity().getId();
        }

        buf.writeInt(subjectId);
        buf.writeInt(objectId);

        buf.writeMap(clientScriptEvent.getValues(), PacketByteBuf::writeString, MappetByteBuffer::writeValue);
    }

    public static ClientScriptEvent readClientScriptEvent(PacketByteBuf buf) {
        String script = buf.readString();
        String function = buf.readString();

        int subjectId = buf.readInt();
        int objectId = buf.readInt();

        Map<String, Object> values = buf.readMap(PacketByteBuf::readString, MappetByteBuffer::readValue);

        ClientScriptEvent clientScriptEvent = ClientScriptEvent.create(
                script,
                function,
                MinecraftClient.getInstance().world.getEntityById(subjectId),
                MinecraftClient.getInstance().world.getEntityById(objectId),
                MinecraftClient.getInstance().world
        );

        clientScriptEvent.setValues(values);

        return clientScriptEvent;
    }

    public static void writeScriptVector(PacketByteBuf buf, ScriptVector scriptVector) {
        buf.writeDouble(scriptVector.x);
        buf.writeDouble(scriptVector.y);
        buf.writeDouble(scriptVector.z);
    }

    public static ScriptVector readScriptVector(PacketByteBuf buf) {
        return new ScriptVector(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeValue(PacketByteBuf buf, Object value) {
        ValueType.fromObject(value).write(buf, value);
    }

    public static Object readValue(PacketByteBuf buf) {
        String typeName = buf.readString();
        return ValueType.fromTypeName(typeName).read(buf);
    }

    public static void writeTriggerList(PacketByteBuf buf, List<Trigger> list) {
        buf.writeInt(list.size());
        list.forEach(trigger -> {
            buf.writeString(trigger.getId());
            DataStorageUtils.writeToPacket(buf, trigger.toData());
        });
    }

    public static List<Trigger> readTriggerList(PacketByteBuf buf) {
        int size = buf.readInt();
        List<Trigger> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Trigger trigger = Mappet.getTriggers().create(Mappet.link(buf.readString()));

            trigger.fromData(DataStorageUtils.readFromPacket(buf));

            list.add(trigger);
        }

        return list;
    }

    private enum ValueType {
        STRING(String.class, "String",
                (buf, obj) -> buf.writeString((String) obj),
                PacketByteBuf::readString),

        VECTOR(ScriptVector.class, "Vector",
                (buf, obj) -> writeScriptVector(buf, (ScriptVector) obj),
                MappetByteBuffer::readScriptVector),

        ITEM_STACK(ItemStack.class, "ItemStack",
                (buf, obj) -> buf.writeItemStack((ItemStack) obj),
                PacketByteBuf::readItemStack),

        DOUBLE(Double.class, "Double",
                (buf, obj) -> buf.writeDouble((Double) obj),
                PacketByteBuf::readDouble),

        FLOAT(Float.class, "Float",
                (buf, obj) -> buf.writeFloat((Float) obj),
                PacketByteBuf::readFloat),

        INTEGER(Integer.class, "Integer",
                (buf, obj) -> buf.writeInt((Integer) obj),
                PacketByteBuf::readInt),

        SHORT(Short.class, "Short",
                (buf, obj) -> buf.writeShort((Short) obj),
                PacketByteBuf::readShort),

        BYTE(Byte.class, "Byte",
                (buf, obj) -> buf.writeByte((Byte) obj),
                PacketByteBuf::readByte);

        private final Class<?> typeClass;
        private final String typeName;
        private final BiConsumer<PacketByteBuf, Object> writer;
        private final Function<PacketByteBuf, Object> reader;

        ValueType(Class<?> typeClass, String typeName,
                  BiConsumer<PacketByteBuf, Object> writer,
                  Function<PacketByteBuf, Object> reader) {
            this.typeClass = typeClass;
            this.typeName = typeName;
            this.writer = writer;
            this.reader = reader;
        }

        public void write(PacketByteBuf buf, Object value) {
            buf.writeString(typeName);
            writer.accept(buf, value);
        }

        public Object read(PacketByteBuf buf) {
            return reader.apply(buf);
        }

        public static ValueType fromObject(Object value) {
            if (value == null) {
                throw new IllegalArgumentException("Value cannot be null");
            }

            for (ValueType type : values()) {
                if (type.typeClass.isInstance(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unsupported type: " + value.getClass().getSimpleName());
        }

        public static ValueType fromTypeName(String typeName) {
            for (ValueType type : values()) {
                if (type.typeName.equals(typeName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown type name: " + typeName);
        }
    }
}