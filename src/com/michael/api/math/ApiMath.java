package com.michael.api.math;

import java.util.ArrayList;

/**
 * @author Michael Risher
 * @since December 28, 2012
 * @version 1.0
 */

public class ApiMath {
	public static final double E = 2.7182818284590452354;
	public static final double PI = 3.14159265358979323846;

	/**
	 * Get the Average of an array of numbers
	 * @param nums
	 * @return Average
	 */
	public static int average(int...nums){
		int total = 0;
		for(int x:nums){
			total += x;
		}
		return total/nums.length;
	}

	/**
	 * Get the Average of an array of nums
	 * @param nums
	 * @return Average
	 */
	public static float average(float...nums){
		int total = 0;
		for(float x:nums){
			total += x;
		}
		return total/nums.length;
	}

	/**
	 * Get the Mean of an array of nums
	 * @param nums
	 * @return Mean
	 */
	public static int mean(int...nums){
		return average(nums);
	}

	/**
	 * Get the Mean of an array of nums
	 * @param nums
	 * @return Mean
	 */
	public static float mean(float...nums){
		return average(nums);
	}

	/**
	 * Get the power
	 * @param b Base
	 * @param p Power
	 * @return Power of base
	 */
	public static int power(int b, int p){
		if(p == 0){
			return 1;
		}
		else{
			if(p > 0){
				return power(b, p - 1) * b;
			}
			else{
				return (1 / power(b, p + 1) * b);
			}

		}
	}

	/**
	 * Get the power
	 * @param c Base
	 * @param p Power
	 * @return Power of base
	 */
	public static float power(float c, float p){
		if(p == 0f){
			return 1f;
		}
		else{
			if(p > 0){
				return power(c, p - 1f) * c;
			}
			else{
				return (1f / power(c, p + 1f) * c);
			}
		}
	}

	/**
	 * Returns the Absolute
	 * @param x
	 * @return Absolute Value of x
	 */
	public static int abs(int x){
		return (x < 0) ? -x : x;
	}

	/**
	 * Returns the Absolute
	 * @param x
	 * @return Absolute Value of x
	 */
	public static float abs(float x){
		return (x < 0) ? -x : x;
	}

	/**
	 * Returns the Absolute
	 * @param x
	 * @return Absolute Value of x
	 */
	public static double abs(double x){
		return (x < 0) ? -x : x;
	}

	/**
	 * Returns the Absolute
	 * @param x
	 * @return Absolute Value of x
	 */
	public static long abs( long x ){
		return ( x < 0 ) ? -x : x;
	}

	/**
	 * Finds the max value
	 * @param nums
	 * @return Max value
	 */
	public static int max(int...nums){
		int m = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i] > m){
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * Finds the max value
	 * @param nums
	 * @return Max value
	 */
	public static float max(float...nums){
		float m = Float.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i] > m){
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * Finds the max value
	 * @param nums
	 * @return Max value
	 */
	public static double max(double...nums){
		double m = Double.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i] > m){
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * Finds the min value
	 * @param nums
	 * @return Min value
	 */
	public static int min(int...nums){
		int m = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i] < m){
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * Finds the min value
	 * @param nums
	 * @return Min value
	 */
	public static float min(float...nums){
		float m = Float.MAX_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i] < m){
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * Finds the min value
	 * @param nums
	 * @return Min value
	 */
	public static double min(double...nums){
		double m = Double.MAX_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i] < m){
				m = nums[i];
			}
		}
		return m;
	}

	/**
	 * Get a factorial x!
	 * @param n
	 * @return factorial
	 */
	public static int factorial(int n){
		if(n <= 1){
			return 1;
		}
		else{
			return n * factorial(n-1);
		}

	}

	/**
	 * Get a factorial x!
	 * @param n
	 * @return factorial
	 */
	public static long factorial(long n){
		if(n <= 1){
			return 1;
		}
		else{
			return n * factorial(n-1);
		}

	}

	/**
	 * Gives the amount with compound interest put to it
	 * uses the formula  b( 1 + i )^n
	 * @param p principal
	 * @param i interest
	 * @param n years
	 * @return total money
	 */
	public static float compundInterest(float p, float i, float n){
		return compundInterest(p, i, n, 1f);
	}

	/**
	 * Gives the amount with compound interest put to it
	 * uses the formula  b( 1 + i )^n
	 * @param p principal
	 * @param i interest
	 * @param n years
	 * @param ntimes times compounded per year
	 * @return total money
	 */
	public static float compundInterest(float p, float i, float n, float ntimes){
		return (p * power(1f + (i/ntimes), ntimes*n));
	}

	/**
	 * find a fibonacci number in the sequence
	 * @param n
	 * @return the number in the sequence
	 */
	public static int fibonacci(int n){
		if (n < 2) {
			return n;
		}
		else {
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}

	/**
	 * find a fibonacci number in the sequence
	 * @param n
	 * @return the number in the sequence
	 */
	public static Long fibonacciL(long n){
		if (n < 2) {
			return n;
		}
		else {
			return fibonacciL(n-1) + fibonacciL(n-2);
		}
	}

	/**
	 * gets the range of the numbers
	 * @param nums
	 * @return range
	 */
	public static int range(int...nums){
		return max(nums) - min(nums);
	}

	/**
	 * gets the range of the numbers
	 * @param nums
	 * @return range
	 */
	public static float range(float...nums){
		return max(nums) - min(nums);
	}

	/**
	 * Find the mode in an array of numbers
	 * @param nums
	 * @return
	 */
	public static int mode(int...nums){
		ArrayList<Integer> nA = new ArrayList<Integer>();

		for(int i = 0; i < max(nums)+1; i++){
			nA.add(0);
		}

		for(int i = 0; i < nums.length; i++){
			int element = nA.get(nums[i])+1;
			nA.set(nums[i], element);
		}

		int m = Integer.MIN_VALUE;
		for (int i = 0; i < nA.size(); i++) {
			if(nA.get(i) > m){
				m = nA.get(i);
			}
		}

		return nA.indexOf(m);
	}

	/**
	 * Find the mode in an array of numbers
	 * @param nums
	 * @return
	 */
	public static float mode(float...nums){
		ArrayList<Float> nA = new ArrayList<Float>();

		for(int i = 0; i < max(nums)+1; i++){
			nA.add(0f);
		}

		for(int i = 0; i < nums.length; i++){
			float element = nA.get((int)nums[i])+1f;
			nA.set((int)nums[i], element);
		}

		float m = Float.MIN_VALUE;
		for (int i = 0; i < nA.size(); i++) {
			if(nA.get(i) > m){
				m = nA.get(i);
			}
		}

		return nA.indexOf(m);
	}

	/**
	 * Find the mode in an array of numbers
	 * @param nums
	 * @return
	 */
	public static double mode(double...nums){
		ArrayList<Double> nA = new ArrayList<Double>();

		for(int i = 0; i < max(nums)+1; i++){
			nA.add(0.0);
		}

		for(int i = 0; i < nums.length; i++){
			double element = nA.get((int)nums[i])+1D;
			nA.set((int)nums[i], element);
		}

		double m = Float.MIN_VALUE;
		for (int i = 0; i < nA.size(); i++) {
			if(nA.get(i) > m){
				m = nA.get(i);
			}
		}

		return nA.indexOf(m);
	}

	/**
	 * Finds the greatest common divisor of two numbers
	 * @param n1
	 * @param n2
	 * @return The gcd
	 */
	public static int gcd(int n1, int n2){
		int[] gcdOn1 = new int[n1];
		int[] gcdOn2 = new int[n2];

		int j = 0, j1 = 0;
		for (int i = 1; i < max(n1, n2); i++) {
			if(n1 % i == 0){
				gcdOn1[j1++] = i;
			}

			if(n2 % i == 0){
				gcdOn2[j++] =  i;
			}
		}

		for (int k = gcdOn1.length-1; k > 0; k--) {
			for (int i1 = gcdOn2.length - 1; i1 > 0; i1--) {
				if(gcdOn1[k] == gcdOn2[i1] && gcdOn1[k] != 0 && gcdOn2[i1] != 0){
					return gcdOn1[k];
				}
			}
		}
		return -1;
	}

	/**
	 * Finds the greatest common divisor of two numbers
	 * @param n1
	 * @param n2
	 * @return The gcd
	 */
	public static float gcd(float n1, float n2){
		float[] gcdOn1 = new float[(int) n1];
		float[] gcdOn2 = new float[(int) n2];

		int j = 0, j1 = 0;
		for (int i = 1; i < max(n1, n2); i++) {
			if(n1 % i == 0){
				gcdOn1[j1++] = i;
			}

			if(n2 % i == 0){
				gcdOn2[j++] =  i;
			}
		}

		for (int k = gcdOn1.length-1; k > 0; k--) {
			for (int i1 = gcdOn2.length - 1; i1 > 0; i1--) {
				if(gcdOn1[k] == gcdOn2[i1] && gcdOn1[k] != 0 && gcdOn2[i1] != 0){
					return gcdOn1[k];
				}
			}
		}
		return -1;
	}

	/**
	 * Finds the greatest common divisor of two numbers
	 * @param n1
	 * @param n2
	 * @return The gcd
	 */
	public static double gcd(double n1, double n2){
		double[] gcdOn1 = new double[(int) n1];
		double[] gcdOn2 = new double[(int) n2];

		int j = 0, j1 = 0;
		for (int i = 1; i < max(n1, n2); i++) {
			if(n1 % i == 0){
				gcdOn1[j1++] = i;
			}

			if(n2 % i == 0){
				gcdOn2[j++] =  i;
			}
		}

		for (int k = gcdOn1.length-1; k > 0; k--) {
			for (int i1 = gcdOn2.length - 1; i1 > 0; i1--) {
				if(gcdOn1[k] == gcdOn2[i1] && gcdOn1[k] != 0 && gcdOn2[i1] != 0){
					return gcdOn1[k];
				}
			}
		}
		return -1;
	}


}