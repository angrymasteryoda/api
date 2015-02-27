package com.michael.api.json;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 6:01 PM
 */
public class Null {
	@Override
	protected final Object clone() {
		return this;
	}

	@Override
	public boolean equals(Object object) {
		return object == null || object == this;
	}

	public String toString() {
		return "null";
	}
}
