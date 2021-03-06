/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Tin Švagelj <tin.svagelj.email@gmail.com> a.k.a. Caellian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package hr.caellian.math.matrix

import hr.caellian.math.vector.VectorF

/**
  * Utility object containing initializers for basic 3x3 matrices.
  * These functions should be used instead of any provided by {@link MatrixF}
  * wherever possible as they are supposed to perform faster.
  *
  * @author Caellian
  */
object Matrix3F {
  /**
    * Initializes rotation matrix using forward, up and right vector.
    *
    * @param forward forward vector
    * @param up      up vector
    * @param right   right vector
    * @return rotation matrix
    */
  def initRotationMatrix(forward: VectorF, up: VectorF, right: VectorF): MatrixF = {
    new MatrixF(Array(right.asArray, up.asArray, forward.asArray))
  }

  /**
    * Initializes a rotation matrix.
    *
    * @param a     first argument.
    * @param b     second argument.
    * @param angle degrees to rotate in objects multiplied by this rotation matrix.
    * @return plane rotation matrix.
    */
  def initPlaneRotation(a: Int, b: Int, angle: Float): MatrixF = {
    val ai = a - 1
    val bi = b - 1
    val matrix = Array.ofDim[Float](4, 4)
    for (x <- matrix.indices) {
      for (y <- matrix(0).indices) {
        val value = (y, x) match {
          case (`ai`, `ai`) => Math.cos(Math.toRadians(angle)).toFloat
          case (`bi`, `bi`) => Math.cos(Math.toRadians(angle)).toFloat
          case (`ai`, `bi`) => -Math.sin(Math.toRadians(angle)).toFloat
          case (`bi`, `ai`) => Math.sin(Math.toRadians(angle)).toFloat
          case _ => {
            if (x == y) {
              1
            } else {
              0
            }
          }
        }
        matrix(y)(x) = value
      }
    }
    MatrixF(matrix)
  }

  /**
    * Initializes rotation matrix using degree rotation in x, y & z direction.
    *
    * @param x degree rotation in x direction
    * @param y degree rotation in y direction
    * @param z degree rotation in z direction
    * @return rotation matrix
    */
  def initRotationMatrix(x: Float, y: Float, z: Float): MatrixF = {
    val rx = Matrix3F.initPlaneRotation(2, 3, x)
    val ry = Matrix3F.initPlaneRotation(3, 1, y)
    val rz = Matrix3F.initPlaneRotation(1, 2, z)
    MatrixF((rz * ry * rx).arrayClone)
  }

  /**
    * Initializes a new 3x3 scaling matrix.
    *
    * @param scale scale.
    * @return scale matrix.
    */
  def initScalingMatrix(scale: Array[Float]): MatrixF = {
    assert(scale.length == 3, "Translation must have 3 values!")
    new MatrixF(Array.tabulate[Float](3, 3)((x, y) => {
      if (x == y) {
        scale(x)
      } else {
        0
      }
    }))
  }

  /**
    * Initializes a new 3x3 translation matrix.
    *
    * @param location relative location.
    * @return translation matrix.
    */
  def initTranslationMatrix(location: Array[Float]): MatrixF = {
    assert(location.length == 2, "Translation must have 2 coordinates!")
    new MatrixF(Array.tabulate[Float](3, 3)((x,
                                             y) => {
      if (x == y) {
        1
      } else if (y == location.length && x < location.length) {
        location(x)
      } else {
        0
      }
    }))
  }

  /**
    * Initializes a new 3x3 identity matrix.
    *
    * @return identity matrix.
    */
  def initIdentityMatrix(): MatrixF = {
    new MatrixF(Array.tabulate[Float](3, 3)((x, y) => {
      if (x == y) {
        1
      } else {
        0
      }
    }))
  }
}
