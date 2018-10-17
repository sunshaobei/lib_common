
package com.sunsh.baselibrary.utils;


import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7frMCwm/DhYPtcddYJ4pEV5xzfk+iQFmYlLG82WBEk0QyNxMpa7XXTlg5i8FIeSwoiTZItrPiFDwYKCVQ32yYmJ/dJiblssDl0Cd/o0J8CZkjmh89GGpknQigQzTxgMTnp88/YpgdsMKZn+ETM1VuztcEwUy3TiQDXM1nw/pb1QIDAQAB";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * 公钥加密
	 * 
	 * @param source
	 *            源数据
	 * @param //publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String source) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = getPublicKey(PUBLIC_KEY);
		// 对数据加密
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		byte[] data = source.getBytes();
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;

		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return Base64.encode(encryptedData);
	}

	public static PublicKey getPublicKey(String public_key_str) {
		KeyFactory keyFactory;
		PublicKey pubKey = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(public_key_str);
			pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pubKey;
	}
}
