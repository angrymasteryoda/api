package com.michael.api.math;

import java.util.ArrayList;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:51 PM
 */
public class EquationSolver {
	private static final char[] MATH_SYMBOL = {'+','-','*','/','^'};
	private static final char[] NUMBERS = {'1','2','3','4','5','6','7','8','9','0'};
	private static final String[] OPERATORS_NAMES = {
		"addition",
		"subtraction",
		"multiplication",
		"division"};
	private static final char[] OPERATORS = {'+','-','*','/'};

	public static String solveEquation(String equ){
		//set a constant of the equ
		//set the result with default value
		String result = "";
		//if (!isEquationSolvable(equ)) {return "Umm to complex is everything right with that";}

		//look to see how many operations are in the equation
		ArrayList<String> numbers = new ArrayList<String>(0);
		ArrayList<Character> operations = new ArrayList<Character>(0);

		//the final equation with no ( )
		String finalEqu = "";

		for (int i = 0; i < equ.length(); i++) {
			String newEqu = "";
			if(equ.charAt(i) == '('){
				short count = 1;
				for (int j = i+1; count != 0; j++) {
					if (equ.charAt(j) == '(') {
						count++;
					}
					else if(equ.charAt(j) == ')'){
						count--;
					}
					else{
						newEqu += "" + equ.charAt(j);
					}
				}

				String evaluatedString = solveEquation(newEqu);
				finalEqu += evaluatedString;
				i+= (newEqu.length());
			}
			else{
				finalEqu += equ.charAt(i);
			}
		}


		for (int i = 0; i < finalEqu.length(); i++) {
			if((int)finalEqu.charAt(i) >= (int)'0' && (int)finalEqu.charAt(i) <= (int)'9'){
				String temp = "";
				boolean negative = false;
				for(int j = i; j < finalEqu.length(); j++){
					if(finalEqu.charAt(j) >= '0' && finalEqu.charAt(j) <= '9'){
						temp += "" + finalEqu.charAt(j);
						//see if the number is negative
						try {
							if (finalEqu.charAt(j-1) == '-') {
								if(j == 1 || isCharMathSymbol(finalEqu.charAt(j-2))){
									negative = true;
									//finalEqu = removeLastNegative(finalEqu);
									operations.remove(operations.size()-1);
								}
							}
						}
						catch (Exception e) {
							//System.err.println("i= "+i+"\nj= "+j);
							//e.printStackTrace();
						}
					}
					else break;
				}

				if(negative){
					numbers.add("-"+temp);
				}
				else{
					numbers.add(temp);
				}
				//System.out.println(numbers);
				i += temp.length() == 0 ? 0 : (temp.length() - 1);
			}
			else if(finalEqu.charAt(i) == '+'
				|| finalEqu.charAt(i) == '-'
				|| finalEqu.charAt(i) == '*'
				|| finalEqu.charAt(i) == '/'
				|| finalEqu.charAt(i) == '^'){
				operations.add(finalEqu.charAt(i));
			}
		}

		//System.out.println(numbers);
		//System.out.println(operations);

		//do the math in order of pemdas
		//check for powers
		for (int i = 0; i < operations.size(); i++) {
			if(operations.get(i).equals(new Character('^'))){
				result = doOperation(numbers.get(i), operations.get(i), numbers.get(i+1));
				operations.set(i, null);
				numbers.set(i, result);
				numbers.set(i+1, null);
				operations.remove(i);
				numbers.remove(i+1);
				i--;
			}
		}

		//check for multiply/divide
		for (int i = 0; i < operations.size(); i++) {
			if(operations.get(i).equals(new Character('*')) || operations.get(i).equals(new Character('/'))){
				result = doOperation(numbers.get(i), operations.get(i), numbers.get(i+1));
				operations.set(i, null);
				numbers.set(i, result);
				numbers.set(i+1, null);
				operations.remove(i);
				numbers.remove(i+1);
				i--;
			}
		}

		//check for the addition/subtraction operation
		for (int i = 0; i < operations.size(); i++) {
			if(operations.get(i).equals(new Character('+')) || operations.get(i).equals(new Character('-'))){
				result = doOperation(numbers.get(i), operations.get(i), numbers.get(i+1));
				operations.set(i, null);
				numbers.set(i, result);
				numbers.set(i+1, null);
				operations.remove(i);
				numbers.remove(i+1);
				i--;
			}
		}

		//end i finished solving return the result
		return result;
	}

	private static String doOperation(String lhs, char op, String rhs){
		String l = lhs;
		String o = ""+op;
		String r = rhs;
		return doOperation(l,o,r);
	}

	private static String doOperation(String lhs, String op, String rhs){
		switch(op){
			case "+":
				return "" + (stringToInt(lhs) + stringToInt(rhs));
			case "-":
				return "" + (stringToInt(lhs) - stringToInt(rhs));
			case "*":
				return "" + (stringToInt(lhs) * stringToInt(rhs));
			case "/":
				return "" + (stringToInt(lhs) / stringToInt(rhs));
			case "^":
				return "" + ApiMath.power(stringToInt(lhs), stringToInt(rhs));
		}
		return "";
	}

	@SuppressWarnings("unused")
	@Deprecated
	private static boolean isEquationSolvable(String equ){
		if(isEquationVariableFree(equ)){
			//check for parnetheis
			char op = '(';
			char cp = ')';
			short count = 0;
			//if any unclosed parnetheis stop
			for (int i = 0; i < equ.length(); i++) {
				if(equ.charAt(i) == op){
					count++;
				}
				else if(equ.charAt(i) == cp){
					count--;
				}
			}
			if(count == 0){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	private static boolean isEquationVariableFree(String equ){
		for (int i = 0; i < equ.length(); i++) {
			if(!isCharNumber(equ.charAt(i))){
				if(!isCharMathSymbol(equ.charAt(i))){
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isCharMathSymbol(char c){
		for (int i = 0; i < MATH_SYMBOL.length; i++) {
			if(c == MATH_SYMBOL[i]){
				return true;
			}
		}
		return false;
	}
	private static boolean isCharNumber(char c){
		for (int i = 0; i < NUMBERS.length; i++) {
			if(c == NUMBERS[i]){
				return true;
			}
		}
		return false;
	}

	private static int stringToInt(String equ){
		try {
			return Integer.parseInt(equ);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static char[] getOperators() {
		return OPERATORS;
	}

	public static String[] getOperators_Names() {
		return OPERATORS_NAMES;
	}

	public static char[] getNumbers() {
		return NUMBERS;
	}

	public static char[] getMathSymbol() {
		return MATH_SYMBOL;
	}
}