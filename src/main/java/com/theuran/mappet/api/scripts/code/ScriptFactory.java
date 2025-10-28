package com.theuran.mappet.api.scripts.code;

import com.caoccao.javet.values.reference.V8ValueObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.theuran.mappet.api.scripts.ScriptLogger;
import com.theuran.mappet.api.scripts.code.bbs.ScriptForm;
import com.theuran.mappet.api.scripts.code.bbs.ScriptTransform;
import com.theuran.mappet.api.scripts.code.block.ScriptBlockState;
import com.theuran.mappet.api.scripts.code.nbt.ScriptNbtCompound;
import com.theuran.mappet.utils.FormUtils;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.utils.pose.Transform;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ScriptFactory {
    public ScriptVector vector(double x, double y, double z) {
        return new ScriptVector(x, y, z);
    }

    public ScriptLogger logger() {
        return new ScriptLogger();
    }

    public ScriptForm createForm(String formData) {
        return new ScriptForm(FormUtils.fromData(formData));
    }

    public ScriptTransform createTransform(String transformData) {
        Transform transformDefault = Transform.DEFAULT;
        transformDefault.toData(DataToString.mapFromString(transformData));

        return new ScriptTransform(transformDefault);
    }

    /**
     * Creates new Block State
     *
     * <pre>{@code
     * mappet.createBlockState("minecraft:dirt");
     * }</pre>
     */
    public ScriptBlockState createBlockState(String blockId) {
        Block block = Registries.BLOCK.get(new Identifier(blockId));

        return ScriptBlockState.create(block.getDefaultState());
    }

    /**
     * Creates a new ScriptNbtCompound from the given NBT string
     *
     * <pre>{@code
     * mappet.createCompound("{id:"kruto"}");
     * }</pre>
     */
    public ScriptNbtCompound createCompound(String nbt) {
        NbtCompound tag = new NbtCompound();

        if (nbt != null) {
            tag = jsonToNbt(new Gson().fromJson(nbt, JsonObject.class));
        }

        return new ScriptNbtCompound(tag);
    }

    private static NbtCompound jsonToNbt(JsonObject jsonObject) {
        NbtCompound nbtCompound = new NbtCompound();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if (value.isJsonObject()) {
                nbtCompound.put(key, jsonToNbt(value.getAsJsonObject()));
            } else if (value.isJsonArray()) {
                nbtCompound.put(key, jsonArrayToNbtList(value.getAsJsonArray()));
            } else if (value.isJsonPrimitive()) {
                nbtCompound.putString(key, value.getAsString());
            }
        }
        return nbtCompound;
    }

    private static NbtList jsonArrayToNbtList(JsonArray jsonArray) {
        NbtList nbtList = new NbtList();
        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                nbtList.add(jsonToNbt(element.getAsJsonObject()));
            } else if (element.isJsonArray()) {
                nbtList.add(jsonArrayToNbtList(element.getAsJsonArray()));
            } else if (element.isJsonPrimitive()) {
                nbtList.add(NbtString.of(element.getAsString()));
            }
        }
        return nbtList;
    }

    public String dump(Object object, boolean simple) {
        if (object instanceof V8ValueObject) {
            return object.toString();
        }

        Class<?> clazz = object.getClass();
        StringBuilder output = new StringBuilder(simple ? clazz.getSimpleName() : clazz.getTypeName());

        output.append(" {\n");

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            output.append("    ");

            if (!simple) {
                output.append(this.getModifier(field.getModifiers()));
            }

            output.append(field.getName());

            if (!simple) {
                output.append(" (");
                output.append(simple ? field.getType().getSimpleName() : field.getType().getTypeName());
                output.append(")");
            }

            String value = "";

            try {
                field.setAccessible(true);
                Object o = field.get(object);

                value = o == null ? "null" : o.toString();
            }
            catch (Exception e) {}

            output.append(": ").append(value).append("\n");
        }

        output.append("\n");

        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            output.append("    ");

            if (!simple) {
                output.append(this.getModifier(method.getModifiers()));
            }

            output.append(simple ? method.getReturnType().getSimpleName() : method.getReturnType().getTypeName());
            output.append(" ");
            output.append(method.getName()).append("(");

            int size = method.getParameterCount();

            for (int i = 0; i < size; i++) {
                Class<?> arg = method.getParameterTypes()[i];

                output.append(simple ? arg.getSimpleName() : arg.getTypeName());

                if (i < size - 1)
                {
                    output.append(", ");
                }
            }

            output.append(")").append("\n");
        }

        output.append("}");

        return output.toString();
    }

    private String getModifier(int m)
    {
        String modifier = Modifier.isFinal(m) ? "final " : "";

        if (Modifier.isPublic(m))
        {
            modifier += "public ";
        }
        else if (Modifier.isProtected(m))
        {
            modifier += "protected ";
        }
        else if (Modifier.isPrivate(m))
        {
            modifier += "private ";
        }

        return modifier;
    }

    /**
     * Returns the class name of the given object
     *
     * <pre>{@code
     * mappet.getClassName(c.getPlayer());
     * }</pre>
     */
    public String getClassName(Object value) {
        String classes = value.getClass().toString();
        int beginIndex = classes.lastIndexOf(".") + 1;

        return classes.substring(beginIndex);
    }
}
