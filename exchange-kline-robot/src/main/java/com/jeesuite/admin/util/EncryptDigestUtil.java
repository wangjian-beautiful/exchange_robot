package com.jeesuite.admin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * 数字加验签工具类
 * 
 * @author KD
 * @email KD
 * @date 2016年11月20日 下午4:31:25
 */
public class EncryptDigestUtil {

	private static String encodingCharset = "UTF-8";

	/**
	 * 生成签名消息
	 */
	public static String sign(String valueStr, String keyStr) {
		byte keyInput[] = new byte[64];
		byte keyOutput[] = new byte[64];
		byte key[];
		byte value[];
		try {
			key = keyStr.getBytes(encodingCharset);
			value = valueStr.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		Arrays.fill(keyInput, key.length, 64, (byte) 54);
		Arrays.fill(keyOutput, key.length, 64, (byte) 92);
		for (int i = 0; i < key.length; i++) {
			keyInput[i] = (byte) (key[i] ^ 0x36);
			keyOutput[i] = (byte) (key[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(keyInput);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(keyOutput);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	/**
	 * 生成签名消息
	 * @param aValue  要签名的字符串
	 * @param aKey  签名密钥
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * SHA加密
	 */
	public static String digest(String valueStr) {
		valueStr = valueStr.trim();
		byte value[];
		try {
			value = valueStr.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return toHex(md.digest(value));

	}
	
	/**
	 * 获取明文签名字符串
	 * 
	 * @param paramsMap 待签名的参数
	 * 
	 * @return String 待签名的明文字符串
	 */
	public static String getPlainSignString(HashMap<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			throw new IllegalArgumentException("Map must not null.");
		}
		
		StringBuffer buffer = new StringBuffer(103);
		Iterator<String> it = paramsMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = paramsMap.get(key);
			if (value == null || "".equals(value.trim())) {
				continue;
			}
			
			if (buffer.length() != 0) {
				buffer.append("&");
			} 
			
			buffer.append(key).append("=").append(paramsMap.get(key));
		}
		
		return buffer.toString();
	}

	public static String getPlainSignString(SortedMap<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			throw new IllegalArgumentException("Map must not null.");
		}

		StringBuffer buffer = new StringBuffer(103);
		Iterator<String> it = paramsMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = paramsMap.get(key);
			if (value == null || "".equals(value.trim())) {
				continue;
			}

			if (buffer.length() != 0) {
				buffer.append("&");
			}

			buffer.append(key).append("=").append(paramsMap.get(key));
		}

		return buffer.toString();
	}

	public static void main(String[]args){
		System.out.println(EncryptDigestUtil.hmacSign("method=order&accesskey=ak152291fdfff345e3&price=0.05088300&amount=3.55&tradeType=0&currency=bch_btc","2cad1b8837f5a85fd4e7a0ecb07ea9308d708b41"));
	}
	
}
