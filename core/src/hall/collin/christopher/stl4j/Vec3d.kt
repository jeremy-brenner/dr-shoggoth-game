/*
The MIT License (MIT)

Copyright (c) 2014 CCHall (aka Cyanobacterium aka cyanobacteruim)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package hall.collin.christopher.stl4j

import kotlin.math.sqrt

/**
 * 3-Dimensional Vector implementation inspired by GLSL structure of the same
 * name. This class and all of its fields are final to optimize performance
 * under a functional programming model.
 * @author CCHall
 */
class Vec3d(
        val x: Double,
        val y: Double,
        val z: Double) {


    /**
     * Multiplies this vector by the specified scalar value.
     * @param scale the scalar value
     * @return The multiplied vector
     */
    fun mul(scale: Double) = Vec3d(
            x * scale,
            y * scale,
            z * scale)

    /**
     * Returns the difference vector of
     * this vector and vector t1 (return = this - t1) .
     * @param t1 the other vector
     * @return The vector `this - t2`
     */
    fun sub(t1: Vec3d) = sub(this, t1)

    /**
     * Returns the sum of vectors this vector and t2 (return = this + t1).
     * @param t1 the first vector
     * @return The vector `this + ta`
     */
    fun add(t1: Vec3d) = add(this, t1)

    /**
     * Returns the length of this vector.
     * @return the length of this vector
     */
    fun length() = sqrt(this.x * this.x + this.y * this.y + this.z * this.z)

    /**
     * Returns the normalized equivalent to this vector.
     * @return This vector after normalizing
     */
    fun normalize(): Vec3d {
        val norm = 1.0 / length()
        return Vec3d(
                this.x * norm,
                this.y * norm,
                this.z * norm
        )
    }

    /**
     * Computes the dot product of this vector and vector v1.
     * @param v1 the other vector
     * @return the dot product of this vector and v1
     */
    fun dot(v1: Vec3d) = this.x * v1.x + this.y * v1.y + this.z * v1.z

    /**
     * Returns the hashcode for this `Vec3f`.
     * @return      a hash code for this `Vec3f`.
     */
    override fun hashCode(): Int {
        var bits = 7L
        bits = 31L * bits + java.lang.Double.doubleToLongBits(x)
        bits = 31L * bits + java.lang.Double.doubleToLongBits(y)
        bits = 31L * bits + java.lang.Double.doubleToLongBits(z)
        return (bits xor (bits shr 32)).toInt()
    }

    /**
     * Determines whether or not two 3D points or vectors are equal.
     * Two instances of `Vec3d` are equal if the values of their
     * `x`, `y` and `z` member fields,
     * representing their position in the coordinate space, are the same.
     * @param obj an object to be compared with this `Vec3d`
     * @return `true` if the object to be compared is
     * an instance of `Vec3d` and has
     * the same values; `false` otherwise.
     */
    override fun equals(obj: Any?): Boolean {
        if (obj === this) {
            return true
        }
        if (obj is Vec3d) {
            val v = obj as Vec3d?
            return x == v!!.x && y == v.y && z == v.z
        }
        return false
    }

    /**
     * Returns a `String` that represents the value
     * of this `Vec3f`.
     * @return a string representation of this `Vec3f`.
     */
    override fun toString(): String {
        return "Vec3d[$x, $y, $z]"
    }

    companion object {

        /**
         * Gets the difference vector of vectors t1 and t2 (returns t3 = t1 - t2).
         * @param t1 the first vector
         * @param t2 the second vector
         * @return The vector `t1 - t2`
         */
        fun sub(t1: Vec3d, t2: Vec3d) = Vec3d(
                t1.x - t2.x,
                t1.y - t2.y,
                t1.z - t2.z
        )


        /**
         * Returns the sum of vectors t1 and t2 (return = t1 + t2).
         * @param t1 the first vector
         * @param t2 the second vector
         * @return The vector `t1 + t2`
         */
        fun add(t1: Vec3d, t2: Vec3d) = Vec3d(
                t1.x + t2.x,
                t1.y + t2.y,
                t1.z + t2.z
        )


        /**
         * Returns the vector cross product of vectors v1 and v2.
         * @param v1 the first vector
         * @param v2 the second vector
         * @return The cross product
         */
        fun cross(v1: Vec3d, v2: Vec3d): Vec3d {
            val tmpX: Double
            val tmpY: Double

            tmpX = v1.y * v2.z - v1.z * v2.y
            tmpY = v2.x * v1.z - v2.z * v1.x
            return Vec3d(
                    v1.x * v2.y - v1.y * v2.x,
                    tmpX,
                    tmpY
            )
        }

        /**
         * Calculates the angle between two vectors.
         * @param a A 3D vector
         * @param b Another 3D vector
         * @return The angle between the two vectors, in radians.
         */
        fun getAngle(a: Vec3d, b: Vec3d): Double {
            val AdotB = a.dot(b)
            val A = a.length()
            val B = b.length()
            return Math.acos(AdotB / (A * B))
        }
    }
}
