package com.theuran.mappet.client.api.scripts.code.ui.elements.graphic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public sealed interface Graphic {
    record Box(int color) implements Graphic { }

    record GradientH(int color1, int color2) implements Graphic { }

    record GradientV(int color1, int color2) implements Graphic { }

    record FullTexturedBox(String path, int color) implements Graphic { }

    record Outline(int color, int border) implements Graphic { }
}

