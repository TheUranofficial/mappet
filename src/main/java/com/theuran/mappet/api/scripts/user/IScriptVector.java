package com.theuran.mappet.api.scripts.user;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
* Vector interface
*/

public interface IScriptVector {
    /**
     * Adds another vector to this vector
     *
     * <pre>{@code
     * mappet.vector(434, 34, 443).add(mappet.vector(22, 232, 232));
     * }</pre>
     */
    ScriptVector add(ScriptVector other);

    /**
     * Subtracts another vector from this vector
     *
     * <pre>{@code
     * mappet.vector(434, 34, 443).subtract(mappet.vector(22, 232, 232));
     * }</pre>
     */
    ScriptVector subtract(ScriptVector other);

    /**
     * Multiplies this vector by a scalar value
     *
     * <pre>{@code
     * mappet.vector(434, 34, 443).multiply(2);
     * }</pre>
     */
    ScriptVector multiply(double scalar);

    ScriptVector cross(ScriptVector vector);

    /**
     * Calculates the length of this vector
     */
    double length();

    /**
     * Returns a normalized version of this vector
     */
    ScriptVector normalize();

    /**
     * Converts this vector to a BlockPos object
     */
    BlockPos toBlockPos();

    /**
     * Converts this vector to a Vec3d object
     */
    Vec3d toVec3d();

    /**
     * Returns a string representation of this vector
     */
    String toString();

    /**
     * Returns a string representation of this vector as an array
     */
    String toArrayString();
}
