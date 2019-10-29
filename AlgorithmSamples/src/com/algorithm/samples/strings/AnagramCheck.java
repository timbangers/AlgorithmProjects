package com.algorithm.samples.strings;

import java.util.Arrays;

public class AnagramCheck {
	
	
	
	public static void main(String[] args) {
		
		String word1 = "stop";
		String word2 = "pots";
		
		char [] charArray1 = word1.toCharArray();
		char [] charArray2 = word2.toCharArray();
		
		Arrays.sort(charArray1);
		Arrays.sort(charArray2);
		
		System.out.println(charArray1);
		System.out.println(charArray2);



	}
	
	
}
