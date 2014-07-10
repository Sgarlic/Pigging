package com.boding.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionsUtil {
	public static boolean checkPassword(String str){
		String expressions = "^[\\x21-\\x7E]{6,20}$";
		return checkIfMatchPattern(str, expressions);
	}
	
	public static boolean checkPhone(String str){
		String expressions = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
		return checkIfMatchPattern(str, expressions);
	}
	
	public static boolean checkZipCode(String str){
		String expressions = "^[\\x21-\\x7E]{6,20}$";
		return checkIfMatchPattern(str, expressions);
	}
	
	public static boolean checkMobile(String str){
		String expressions = "^13[0-9]{9}|15[012356789][0-9]{8}|18[0256789][0-9]{8}";
		return checkIfMatchPattern(str, expressions);
	}
	
	public static boolean checkIfChinese(String str) {
		String expressions = "[\u4E00-\u9FA5]";
		return checkIfMatchPattern(str, expressions);
	}
	
	private static boolean checkIfMatchPattern(String str,String expressions){
		boolean mark = false;
		Pattern pattern = Pattern.compile(expressions);
		Matcher matc = pattern.matcher(str);
		StringBuffer stb = new StringBuffer();
		while (matc.find()) {
			mark = true;
		    stb.append(matc.group());
		}
		return mark;
	}
}
