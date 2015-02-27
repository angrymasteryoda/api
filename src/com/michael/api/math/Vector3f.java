package com.michael.api.math;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:38 PM
 */
public class Vector3f {

	private float x;
	private float y;
	private float z;

	public Vector3f( float x, float y, float z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float length() {
		return (float) Math.sqrt( x * x + y * y + z * z );
	}

	public float dot( Vector3f v ) {
		return x * v.getX() + y * v.getY() + z * v.getZ();
	}

	public Vector3f normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;

		//return this vector
		return this;
	}

	public Vector3f rotate( float angle ) {
		return null;
	}

	public Vector3f add( Vector3f v ) {
		return new Vector3f( x + v.getX(), y + v.getY(), z + v.getZ() );
	}

	public Vector3f add( float v ) {
		return new Vector3f( x + v, y + v, z + v );
	}

	public Vector3f subtract( Vector3f v ) {
		return new Vector3f( x - v.getX(), y - v.getY(), z - v.getZ() );
	}

	public Vector3f subtract( float v ) {
		return new Vector3f( x - v, y - v, z - v );
	}

	public Vector3f multiply( Vector3f v ) {
		return new Vector3f( x * v.getX(), y * v.getY(), z * v.getZ() );
	}

	public Vector3f multiply( float v ) {
		return new Vector3f( x * v, y * v, z * v );
	}

	public Vector3f divide( Vector3f v ) {
		return new Vector3f( x / v.getX(), y / v.getY(), z / v.getZ() );
	}

	public Vector3f divide( float v ) {
		return new Vector3f( x / v, y / v, z / v );
	}

	public Vector3f cross( Vector3f v ) {
		float x_ = y * v.getZ() - z * v.getY();
		float y_ = z * v.getX() - x * v.getZ();
		float z_ = x * v.getY() - y * v.getX();

		return new Vector3f( x_, y_, z_ );
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

	public float getZ() {
		return z;
	}

	public void setZ( float z ) {
		this.z = z;
	}
}