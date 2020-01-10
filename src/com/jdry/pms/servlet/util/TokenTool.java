package com.jdry.pms.servlet.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
/**
 * 安全验证token
 * @author hezuping
 *
 */
public class TokenTool {

	private static final Integer SALT_LENGTH = Integer.valueOf(12);
	private static final String HEX_NUMS_STR = "0123456789ABCDEF";
	private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", 
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 加密，并且每次生成结果不一样
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getEncryptedPwd(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
			{
		byte[] pwd = null;

		SecureRandom random = new SecureRandom();

		byte[] salt = new byte[SALT_LENGTH.intValue()];

		random.nextBytes(salt);

		MessageDigest md = null;

		md = MessageDigest.getInstance("MD5");

		md.update(salt);

		md.update(password.getBytes("UTF-8"));

		byte[] digest = md.digest();

		pwd = new byte[digest.length + SALT_LENGTH.intValue()];

		System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH.intValue());

		System.arraycopy(digest, 0, pwd, SALT_LENGTH.intValue(), digest.length);

		return byteToHexString(pwd);
			}

	/**
	 * 验证是否通过
	 * @param password
	 * @param passwordInDb
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean validPassword(String password, String passwordInDb) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte[] pwdInDb = hexStringToByte(passwordInDb);

		byte[] salt = new byte[SALT_LENGTH.intValue()];

		System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH.intValue());

		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(salt);

		md.update(password.getBytes("UTF-8"));

		byte[] digest = md.digest();

		byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH.intValue()];

		System.arraycopy(pwdInDb, SALT_LENGTH.intValue(), digestInDb, 0, digestInDb.length);

		return Arrays.equals(digest, digestInDb);
	}

	public static byte[] hexStringToByte(String hex)
	{
		int len = hex.length() / 2;
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte)("0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | "0123456789ABCDEF".indexOf(hexChars[(pos + 1)]));
		}
		return result;
	}

	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	private static String byteToString(byte[] bByte)
	{
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	private static String byteToArrayString(byte bByte)
	{
		int iRet = bByte;

		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}
	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");

			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public static void main(String[] args)
			throws NoSuchAlgorithmException, UnsupportedEncodingException
			{
		String str = "ABcd1234**&&";

		String encryptResult = getEncryptedPwd(str);
		System.out.println(encryptResult);

		System.out.println(validPassword(str, "F0D5895730B52AC895240DA2B0E360E76D1E558C138489FBE0BFBF6A"));
			}

}
