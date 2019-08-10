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

import java.util.Arrays


/**
 * This object represents a triangle in 3D space.
 * @author CCHall
 */
class Triangle
/**
 * Creates a triangle with the given vertices at its corners. The normal is
 * calculated by assuming that the vertices were provided in right-handed
 * coordinate space (counter-clockwise)
 * @param v1 A corner vertex
 * @param v2 A corner vertex
 * @param v3 A corner vertex
 */
(v1: Vec3d, v2: Vec3d, v3: Vec3d) {
    /**
     * Gets the vertices at the corners of this triangle
     * @return An array of vertices
     */
    private val vertices: Array<Vec3d?> = arrayOf(v1,v2,v3)
    /**
     * Gets the normal vector
     * @return A vector pointing in a direction perpendicular to the surface of
     * the triangle.
     */
    val normal: Vec3d

    init {
        val edge1 = v2.sub(v1)
        val edge2 = v3.sub(v1)
        normal = Vec3d.cross(edge1, edge2).normalize()
    }

    /**
     * Moves the triangle in the X,Y,Z direction
     * @param translation A vector of the delta for each coordinate.
     */
    fun translate(translation: Vec3d) {
        for (i in vertices.indices) {
            vertices[i] = vertices[i]!!.add(translation)
        }
    }

    /**
     * @see java.lang.Object.toString
     * @return A string that provides some information about this triangle
     */
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Triangle[")
        for (v in vertices) {
            sb.append(v.toString())
        }
        sb.append("]")
        return sb.toString()
    }

    /**
     * @see java.lang.Object.equals
     * @param obj Object to test equality
     * @return True if the other object is a triangle whose verticese are the
     * same as this one.
     */
    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }
        val other = obj as Triangle?
        return Arrays.deepEquals(this.vertices, other!!.vertices)
    }

    /**
     * @see java.lang.Object.hashCode
     * @return A hashCode for this triangle
     */
    override fun hashCode(): Int {
        var hash = 7
        hash = 67 * hash + Arrays.deepHashCode(this.vertices)
        return hash
    }
}
