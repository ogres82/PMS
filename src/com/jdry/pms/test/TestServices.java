package com.jdry.pms.test;


public class TestServices {
	public static String str = "fjd789klsd908434jk#$$%%^38488545";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("替换之前的字符串：" + str);
		String substr = str.replaceAll("[^0-9]", "");
		System.out.println("替换之后的字符串：" + substr);
	}
}
