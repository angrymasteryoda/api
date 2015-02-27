package com.michael.api.math;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:38 PM
 */
public class Vector2f {

	private float x;
	private float y;

	/**
	 * create 2 point float vector
	 * @param x
	 * @param y
	 */
	public Vector2f( float x, float y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * gets length of vector
	 * @return float length
	 */
	public float length() {
		// sqrt( x^2 + y^2 )
		return (float) Math.sqrt( x * x + y * y );
	}

	/**
	 * get dot product between vectors
	 * @param v Vector2f
	 * @return Vector2f
	 */
	public float dot( Vector2f v ) {
		return x * v.getX() + y * v.getY();
	}

	/**
	 * normalize a vector
	 * @return Vector2f
	 */
	public Vector2f normalize() {
		float length = length();
		x /= length;
		y /= length;

		//return this vector
		return this;
	}

	/**
	 * rotate a vector by degrees
	 * @param angle amount of degres to rotate
	 * @return
	 */
	public Vector2f rotate( float angle ) {
		double rad = Math.toRadians( angle );
		double cos = Math.cos( rad );
		double sin = Math.sin( rad );

		return new Vector2f( (float) ( x * cos - y * sin ), (float) ( x * sin + y * cos ) );
	}

	/**
	 * add 2 vectors together
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f add( Vector2f v ) {
		return new Vector2f( x + v.getX(), y + v.getY() );
	}

	/**
	 * add an amount to vector
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f add( float v ) {
		return new Vector2f( x + v, y + v );
	}

	/**
	 * subtract 2 vectors together
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f subtract( Vector2f v ) {
		return new Vector2f( x - v.getX(), y - v.getY() );
	}

	/**
	 * subtract an amount to vector
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f subtract( float v ) {
		return new Vector2f( x - v, y - v );
	}

	/**
	 * multiply 2 vectors together
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f multiply( Vector2f v ) {
		return new Vector2f( x * v.getX(), y * v.getY() );
	}

	/**
	 * multiply an amount to vector
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f multiply( float v ) {
		return new Vector2f( x * v, y * v );
	}

	/**
	 * divide 2 vectors together
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f divide( Vector2f v ) {
		return new Vector2f( x / v.getX(), y / v.getY() );
	}

	/**
	 * divide an amount to vector
	 * @param v vector to add
	 * @return Vector2f
	 */
	public Vector2f divide( float v ) {
		return new Vector2f( x / v, y / v );
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public float getX() {
		return x;
	}

	public void setX( float x ) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY( float y ) {
		this.y = y;
	}
}