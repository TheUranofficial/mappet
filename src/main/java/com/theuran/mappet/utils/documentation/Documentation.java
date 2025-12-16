package com.theuran.mappet.utils.documentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.theuran.mappet.Mappet;
import net.minecraft.client.MinecraftClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Documentation {
    public static final List<Chapter> SERVER_CHAPTERS = new ArrayList<>();
    public static final List<Chapter> CLIENT_CHAPTERS = new ArrayList<>();

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void parseDocs() {
        InputStream inputStream = null;
        try {
            inputStream = MinecraftClient.getInstance().getResourceManager().getResource(Mappet.id("docs.json")).get().getInputStream();
        } catch (Exception ignored) {
        }

        JsonObject docs = GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class);

        parseChapters(docs);
    }

    public static Chapter getServerChapter(String name) {
        for (Chapter serverChapter : SERVER_CHAPTERS) {
            if (serverChapter.name().equals(name)) {
                return serverChapter;
            }
        }

        return null;
    }

    private static void parseChapters(JsonObject docs) {
        if (!SERVER_CHAPTERS.isEmpty() || !CLIENT_CHAPTERS.isEmpty())
            return;

        for (JsonElement jsonElement : docs.getAsJsonArray("classes").asList()) {
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            String description = jsonElement.getAsJsonObject().get("description").getAsString();
            List<Method> methods = new ArrayList<>();

            for (JsonElement method : jsonElement.getAsJsonObject().getAsJsonArray("methods")) {
                parseMethod(methods, method.getAsJsonObject());
            }

            SERVER_CHAPTERS.add(new Chapter(name, description, methods));
        }
    }

    private static void parseMethod(List<Method> methods, JsonObject jsonMethod) {
        String name = jsonMethod.get("name").getAsString();
        String description = jsonMethod.get("description").getAsString();
        List<Argument> arguments = new ArrayList<>();
        String returnn = jsonMethod.get("return").getAsString();

        for (JsonElement jsonElement : jsonMethod.getAsJsonArray("arguments")) {
            parseArgument(arguments, jsonElement.getAsJsonObject());
        }

        methods.add(new Method(name, description, arguments, returnn));
    }

    private static void parseArgument(List<Argument> arguments, JsonObject jsonArgument) {
        String name = jsonArgument.get("name").getAsString();
        String type = jsonArgument.get("type").getAsString();

        arguments.add(new Argument(name, type));
    }
}
