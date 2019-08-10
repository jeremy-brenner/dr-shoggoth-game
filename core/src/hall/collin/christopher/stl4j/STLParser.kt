/*
The MIT License (MIT)

Copyright (c) 2014 CCHall (aka Cyanobacterium aka cyanobacteruim), 2017 Andrew Goh

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

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.util.ArrayList
import java.util.Arrays
import java.util.StringTokenizer
import java.util.logging.Level
import java.util.logging.Logger

/**
 * This class is a parser for STL files. Currently, normals specified in the
 * file are ignored and recalculated under the assumption that the coordinates
 * are provided in right-handed coordinate space (counter-clockwise).
 * @author CCHall
 * @author Andrew Goh
 *
 * * -reversion: mar 2017 Andrew
 * updated logic to handle binary STL files with "solid" in the header
 */
object STLParser {
    /**
     * Parses an STL file, attempting to automatically detect whether the file
     * is an ASCII or binary STL file
     * @param filepath The file to parse
     * @return A list of triangles representing all of the triangles in the STL
     * file.
     * @throws IOException Thrown if there was a problem reading the file
     * (typically means the file does not exist or is not a file).
     * @throws IllegalArgumentException Thrown if the STL is not properly
     * formatted
     */
    @Throws(IOException::class)
    fun parseSTLFile(file: File): List<Triangle> {
        val allBytes = file.readBytes()
        // determine if it is ASCII or binary STL

        //some binary STL files has "solid" in the first 80 chars
        //this breaks logic that determines if a file is ascii based on it
        //simply beginning with "solid"
        var isASCIISTL = false

        //read the first 512 chars or less
        val buf = readblock(allBytes, 0, 512)
        var sb = StringBuffer()
        var inl = readline(buf, sb, 0)
        var line = sb.toString()
        var st = StringTokenizer(line)
        var token = st.nextToken()
        if (token == "solid" && inl > -1) { //found new line for next line
                sb = StringBuffer()
                readline(buf, sb, inl + 1) //read next line
                line = sb.toString()
                st = StringTokenizer(line)
                token = st.nextToken()
                isASCIISTL = when (token) {
                    "endsolid" -> true
                    "facet" -> true
                    else -> false
                }
        }

        return if (isASCIISTL) {
            readASCII(allBytes.toString(Charsets.UTF_8).toLowerCase())
        } else if (isbinaryfile(allBytes)) {
           readBinary(allBytes)
        } else {
            listOf() // isbinaryfile would have thrown
        }
    }


    fun readblock(allBytes: ByteArray, offset: Int, length: Int): String {
        var length = length
        if (allBytes.size - offset < length) length = allBytes.size - offset
        val charset = Charsets.UTF_8
        val decode = charset.decode(ByteBuffer.wrap(allBytes, offset, length))
        return decode.toString().toLowerCase()
    }

    fun readline(buf: String, sb: StringBuffer, offset: Int): Int {
        val il = buf.indexOf('\n', offset)
        if (il > -1)
            sb.append(buf.substring(offset, il - 1))
        else
            sb.append(buf.substring(offset))
        return il
    }

    @Throws(IllegalArgumentException::class)
    fun isbinaryfile(allBytes: ByteArray): Boolean {
        if (allBytes.size < 84)
            throw IllegalArgumentException("invalid binary file, length<84")
        val numtriangles = byteatoint(Arrays.copyOfRange(allBytes, 80, 84))
        if (allBytes.size >= 84 + numtriangles * 50)
            return true //is binary file
        else {
            val msg = "invalid binary file, num triangles does not match length specs"
            throw IllegalArgumentException(msg)
        }
    }

    // little endian
    fun byteatoint(bytes: ByteArray): Int {
        assert(bytes.size == 4)
        var r = bytes[0].toInt() and 0xff
        r = r or (bytes[1].toInt() and 0xff shl 8)
        r = r or (bytes[2].toInt() and 0xff shl 16)
        r = r or (bytes[3].toInt() and 0xff shl 24)
        return r
    }


    /**
     * Reads an STL ASCII file content provided as a String
     * @param content ASCII STL
     * @return A list of triangles representing all of the triangles in the STL
     * file.
     * @throws IllegalArgumentException Thrown if the STL is not properly
     * formatted
     */
    fun readASCII(content: String): List<Triangle> {
        Logger.getLogger(STLParser::class.java.name).log(Level.FINEST, "Parsing ASCII STL format")
        // string is lowercase
        val triangles = ArrayList<Triangle>()

        var position = 0
         run {
             scan@ while ((position < content.length) and (position >= 0)) {
                position = content.indexOf("facet", position)
                if (position < 0) {
                    break@scan
                }
                try {
                    val vertices = arrayOfNulls<Vec3d>(3)
                    for (v in vertices.indices) {
                        position = content.indexOf("vertex", position) + "vertex".length
                        while (Character.isWhitespace(content[position])) {
                            position++
                        }
                        var nextSpace: Int
                        val vals = DoubleArray(3)
                        for (d in vals.indices) {
                            nextSpace = position + 1
                            while (!Character.isWhitespace(content[nextSpace])) {
                                nextSpace++
                            }
                            val value = content.substring(position, nextSpace)
                            vals[d] = java.lang.Double.parseDouble(value)
                            position = nextSpace
                            while (Character.isWhitespace(content[position])) {
                                position++
                            }
                        }
                        vertices[v] = Vec3d(vals[0], vals[1], vals[2])
                    }
                    position = content.indexOf("endfacet", position) + "endfacet".length
                    triangles.add(Triangle(vertices[0]!!, vertices[1]!!, vertices[2]!!))
                } catch (ex: Exception) {
                    var back = position - 128
                    if (back < 0) {
                        back = 0
                    }
                    var forward = position + 128
                    if (position > content.length) {
                        forward = content.length
                    }
                    throw IllegalArgumentException("Malformed STL syntax near \"" + content.substring(back, forward) + "\"", ex)
                }

            }
        }

        return triangles
    }


    /**
     * Parses binary STL file content provided as a byte array
     * @param allBytes binary STL
     * @return A list of triangles representing all of the triangles in the STL
     * file.
     * @throws IllegalArgumentException Thrown if the STL is not properly
     * formatted
     */
    fun readBinary(allBytes: ByteArray): List<Triangle> {
        Logger.getLogger(STLParser::class.java.name).log(Level.FINEST, "Parsing binary STL format")
        val inputStream = DataInputStream(ByteArrayInputStream(allBytes))
        val triangles = ArrayList<Triangle>()
        // skip the header
        val header = ByteArray(80)
        inputStream.read(header)
        // get number triangles (not really needed)
        // WARNING: STL FILES ARE SMALL-ENDIAN
        val numberTriangles = Integer.reverseBytes(inputStream.readInt())
        triangles.ensureCapacity(numberTriangles)
        // read triangles
        try {
            while (inputStream.available() > 0) {
                val nvec = FloatArray(3)
                for (i in nvec.indices) {
                    nvec[i] = java.lang.Float.intBitsToFloat(Integer.reverseBytes(inputStream.readInt()))
                }
                val normal = Vec3d(nvec[0].toDouble(), nvec[1].toDouble(), nvec[2].toDouble()) // not used (yet)
                val vertices = arrayOfNulls<Vec3d>(3)
                for (v in vertices.indices) {
                    val vals = FloatArray(3)
                    for (d in vals.indices) {
                        vals[d] = java.lang.Float.intBitsToFloat(Integer.reverseBytes(inputStream.readInt()))
                    }
                    vertices[v] = Vec3d(vals[0].toDouble(), vals[1].toDouble(), vals[2].toDouble())
                }
                val attribute = java.lang.Short.reverseBytes(inputStream.readShort()) // not used (yet)
                triangles.add(Triangle(vertices[0]!!, vertices[1]!!, vertices[2]!!))
            }
        } catch (ex: Exception) {
            throw IllegalArgumentException("Malformed STL binary at triangle number " + (triangles.size + 1), ex)
        }



        return triangles
    }

}
