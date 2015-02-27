package com.michael.api.math;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:43 PM
 */
public class Matrix4f {

	private float[][] m;

	public Matrix4f() {
		m = new float[4][4];
	}

	public Matrix4f initIdentity() {
	    /*
         1  0  0  0
         0  1  0  0
         0  0  1  0
         0  0  0  1
         */
		for ( int x = 0; x < 4; x++ ) {
			for ( int y = 0; y < 4; y++ ) {
				if ( x == y ) {
					m[x][y] = 1;
				}
				m[x][y] = 0;
			}
		}

		return this;
	}

	public Matrix4f multiply( Matrix4f r ) {
		Matrix4f result = new Matrix4f();

		for ( int x = 0; x < 4; x++ ) {
			for ( int y = 0; y < 4; y++ ) {
				result.set( x, y, m[x][0] * result.get( 0, y ) +
					m[x][1] * result.get( 1, y ) +
					m[x][2] * result.get( 2, y ) +
					m[x][3] * result.get( 3, y )
				);
			}
		}

		return result;

	}

	public float get( int x, int y ) {
		return m[x][y];
	}

	public void set( int x, int y, float v ) {
		m[x][y] = v;
	}

	public float[][] getM() {
		return m;
	}

	public void setM( float[][] m ) {
		this.m = m;
	}
}