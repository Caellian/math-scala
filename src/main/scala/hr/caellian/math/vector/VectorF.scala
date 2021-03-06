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

package hr.caellian.math.vector

import java.nio._

import hr.caellian.math.matrix.{Matrix, MatrixF}
import hr.caellian.math.util.DataUtil

/**
  * Vector class for float N-dimensional vectors.
  *
  * @author Caellian
  */
class VectorF(override val data: Array[Float]) extends Vector[Float] {
  /**
    * @return new conjugated vector.
    */
  def unary_- = {
    new VectorF(asArray.map(-_))
  }

  /**
    * Adds two vectors together and returns resulting vector.
    * In order to add to matrices together, they must be of same size.
    *
    * @param other vector to add to this one.
    * @return result of vector addition.
    */
  def +(other: Vector[Float]): VectorF = {
    require(size == other.size, "Invalid argument vector size!")
    new VectorF(data zip other.data map Function.tupled(_ + _))
  }

  /**
    * Subtracts other vector from this one and returns resulting vector.
    * In order to subtract one vector from another, both vectors must be of same size.
    *
    * @param other vector to subtract from this one.
    * @return result of vector subtraction.
    */
  def -(other: Vector[Float]): VectorF = {
    require(size == other.size, "Invalid argument vector size!")
    new VectorF(data zip other.data map Function.tupled(_ - _))
  }

  /**
    * Multiplies two vectors together and returns resulting vector.
    * In order to add to multiply vectors together, they must be of same size.
    *
    * @param other vector to multiply with this one.
    * @return result of vector multiplication.
    */
  def *(other: Vector[Float]): VectorF = {
    require(size == other.size, "Invalid argument vector size!")
    new VectorF(data zip other.data map Function.tupled(_ * _))
  }

  /**
    * Divides this vector with other and returns resulting vector.
    * In order to divide one vector from another, both vectors must be of same size.
    *
    * @param other vector dividend.
    * @return result of vector division.
    */
  def /(other: Vector[Float]): VectorF = {
    require(size == other.size, "Invalid argument vector size!")
    new VectorF(data zip other.data map Function.tupled(_ / _))
  }

  /**
    * Performs scalar addition on this vector and returns resulting vector.
    *
    * @param value scalar value.
    * @return result of scalar vector addition.
    */
  def +(value: Float): VectorF = {
    new VectorF(asArray.map(_ + value))
  }

  /**
    * Performs scalar subtraction on this vector and returns resulting vector.
    *
    * @param value scalar value.
    * @return result of scalar vector subtraction.
    */
  def -(value: Float): VectorF = {
    new VectorF(asArray.map(_ - value))
  }

  /**
    * Performs scalar multiplication on this vector and returns resulting vector.
    *
    * @param value scalar value.
    * @return result of scalar vector multiplication.
    */
  def *(value: Float): VectorF = {
    new VectorF(asArray.map(_ * value))
  }

  /**
    * Performs scalar division on this vector and returns resulting vector.
    *
    * @param value scalar value.
    * @return result of scalar vector division.
    */
  def /(value: Float): VectorF = {
    new VectorF(asArray.map(_ / value))
  }

  /**
    * @return max value of member of this vector.
    */
  def max: Float = {
    data.toList.max
  }

  /**
    * @return new vector containing absolute values of this vector.
    */
  def absolute: VectorF = {
    new VectorF(asArray.map(Math.abs))
  }

  /**
    * @return new vector with normalized values of this one.
    */
  def normalized: VectorF = {
    this / this.magnitude
  }

  /**
    * @return magnitude of this vector.
    */
  def magnitude: Float = {
    distanceTo(this)
  }

  /**
    * Calculates distance from this to other vector.
    *
    * @param other vector to calculate distance to.
    * @return distance between this and other vector.
    */
  def distanceTo(other: Vector[Float]): Float = {
    Math.sqrt(this dot other).toFloat
  }

  /**
    *
    * @param other other vector used to determine dot product.
    * @return dot product of two vectors.
    */
  def dot(other: Vector[Float]): Float = {
    require(size == other.size, "Vectors must be of same size.")
    (data zip other.data map Function.tupled(_ * _)).sum
  }

  /**
    * Returns cross product of this and other vector.
    *
    * @param other other vector used to determine cross product.
    * @return cross product.
    */
  def cross(other: Vector[Float]): VectorF = {
    require(size == other.size, "Vectors must be of same size.")
    // Some cross product improvisations do exist for 2D space, but they are mathematically incorrect.
    require(size == 7 || size == 3, s"Cross product does not exist in $size-dimensional space!")

    val result = Array.ofDim[Float](this.size)
    this.size match {
      case 7 =>
        result(X) = this (Y) * other(W) - this (W) * other(Y) + this (Z) * other(6) - this (6) * other(Z) + this (4) * other(5) - this (5) * other(4)
        result(Y) = this (Z) * other(4) - this (4) * other(Z) + this (W) * other(X) - this (X) * other(W) + this (5) * other(6) - this (6) * other(5)
        result(Z) = this (W) * other(5) - this (5) * other(W) + this (4) * other(Y) - this (Y) * other(4) + this (6) * other(X) - this (X) * other(6)
        result(W) = this (4) * other(6) - this (6) * other(4) + this (5) * other(Z) - this (Z) * other(5) + this (X) * other(Y) - this (Y) * other(X)
        result(4) = this (5) * other(X) - this (X) * other(5) + this (6) * other(W) - this (W) * other(6) + this (Y) * other(Z) - this (Z) * other(Y)
        result(5) = this (6) * other(Y) - this (Y) * other(6) + this (X) * other(4) - this (4) * other(X) + this (Z) * other(W) - this (W) * other(Z)
        result(6) = this (X) * other(Z) - this (Z) * other(X) + this (Y) * other(5) - this (5) * other(Y) + this (W) * other(4) - this (4) * other(W)
      case 3 =>
        result(X) = this (Y) * other(Z) - this (Z) * other(Y)
        result(Y) = this (Z) * other(X) - this (X) * other(Z)
        result(Z) = this (X) * other(Y) - this (Y) * other(X)
    }
    new VectorF(result)
  }

  /**
    * Rotates this vector using rotation matrix.
    *
    * @param rotationMatrix rotation matrix to use.
    * @return rotated vector.
    */
  def rotated(rotationMatrix: Matrix[Float]): VectorF = {
    (rotationMatrix * verticalMatrix).toVector.asInstanceOf[VectorF]
  }

  /**
    * Linearly interpolates between two vectors.
    *
    * @param destination vector with destination coordinates.
    * @param percent     percentage of
    * @return linear interpolation.
    */
  def lerp(destination: Vector[Float], percent: Float): VectorF = {
    this + (destination - this) * percent
  }

  /**
    * Vertical matrix containing data of this vector.
    */
  val verticalMatrix = MatrixF(true, asArray)

  /**
    * Horizontal matrix containing data of this vector.
    */
  val horizontalMatrix = MatrixF(false, asArray)

  /**
    * @return buffer containing data of this vector.
    */
  def asBuffer: Buffer = {
    ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder).asFloatBuffer().put(data).flip
  }

  /**
    * @return clone of this vector.
    */
  override def replicated: VectorF = new VectorF(asArray)

  /**
    * Creates a new instance of wrapper containing given data.
    *
    * @param data data of new wrapper.
    * @return new instance of wrapper containing argument data.
    */
  override def withData(data: Array[Float]): VectorF = new VectorF(data)

  /**
    * Vector hashcode depends on vector data and will change is vector data is modified!
    *
    * @return hashcode of this vector.
    */
  override def hashCode: Int = {
    DataUtil.hashCode(data)
  }
}

/**
  * Object used to create {@link VectorF} vectors and ensure their consistency.
  * Use initializer functions from this object only if there is no alternative ones in more specific objects as some
  * of these are very CPU intensive.
  *
  * @author Caellian
  */
object VectorF {
  type VecF = VectorF

  /**
    * Creates a new vector using given values.
    *
    * @param values values to create a new vector from.
    * @return created vector.
    */
  def apply(values: Float*): VectorF = {
    new VectorF(values.toArray[Float])
  }

  /**
    * Creates a new vector using array values.
    *
    * @param values value array to create a new vector from.
    * @return created vector.
    */
  def apply(values: Array[Float]): VectorF = {
    new VectorF(values)
  }

  /**
    * Creates a new vector using buffer values.
    *
    * @param buffer buffer to create a new vector from.
    * @return created vector.
    */
  def apply(buffer: FloatBuffer): VectorF = {
    new VectorF((0 until buffer.capacity()).map(buffer.get).toArray)
  }
}


