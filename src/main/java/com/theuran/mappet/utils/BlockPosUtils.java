package com.theuran.mappet.utils;

import net.minecraft.util.math.BlockPos;

public class BlockPosUtils {
    public static BlockPos fromShortString(String str) {
        String[] parts = str.split(", ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        return new BlockPos(x, y, z);
    }
}
