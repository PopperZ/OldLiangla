package com.brightcns.liangla.utils;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * author tech-502
 *
 * create at 2017/09/22
 *
 * on 10:55
 *
 * summary:
 **/
public class DESMacUtils {

  private static final byte[] ZERO_VECTOR = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
  private static final String DEFAULT_VECTOR = "0000000000000000";

  private static byte[] hexStringToByte(String hex) {
    int len = hex.length() / 2;
    byte[] result = new byte[len];
    char[] aChar = hex.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (toByte(aChar[pos]) << 4 | toByte(aChar[(pos + 1)]));
    }
    return result;
  }

  private static byte toByte(char c) {
    //noinspection SpellCheckingInspection
    return (byte) "0123456789ABCDEF".indexOf(c);
  }

  /**
   * byte数组转换为十六进制字符串
   */
  private static String bytesToHexString(byte[] bHex) {

    StringBuilder sbReturn = new StringBuilder(bHex.length);

    for (byte aBHex : bHex) {
      String sTemp = Integer.toHexString(0xFF & aBHex);
      if (sTemp.length() < 2) {
        sbReturn.append(0);
      }
      sbReturn.append(sTemp.toUpperCase());
    }
    return sbReturn.toString();
  }

  /**
   * 将b1和b2做异或，然后返回
   *
   * @return 异或结果
   */
  private static byte[] xOr(byte[] b1, byte[] b2) {
    byte[] tXor = new byte[Math.min(b1.length, b2.length)];
    for (int i = 0; i < tXor.length; i++)
      tXor[i] = (byte) (b1[i] ^ b2[i]); // 异或(Xor)
    return tXor;
  }

  /**
   * 3DES加密cbc模式，默认向量
   *
   * @param content 待加密数据
   * @param key 秘钥
   * @return 加密结果
   * @throws GeneralSecurityException
   */
  public static byte[] encryptBy3DesCbc(byte[] content, byte[] key)
      throws GeneralSecurityException {
    return encryptBy3DesCbc(content, key, ZERO_VECTOR);
  }

  /**
   * 3DES加密cbc模式
   *
   * @param content 待加密数据
   * @param key 秘钥
   * @param ivb 向量
   * @return 加密结果
   * @throws GeneralSecurityException
   */
  public static byte[] encryptBy3DesCbc(byte[] content, byte[] key, byte[] ivb)
      throws GeneralSecurityException {
    byte[] _3DesKey = new byte[24];
    System.arraycopy(key, 0, _3DesKey, 0, 16);
    System.arraycopy(key, 0, _3DesKey, 16, 8);

    Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
    SecretKey secureKey = new SecretKeySpec(_3DesKey, "DESede");
    IvParameterSpec iv = new IvParameterSpec(ivb);
    cipher.init(Cipher.ENCRYPT_MODE, secureKey, iv);
    return cipher.doFinal(content);
  }

  public static byte[] encryptByDesCbc(byte[] content, byte[] key, byte[] icv)
      throws GeneralSecurityException {
    SecureRandom sr = new SecureRandom();
    DESKeySpec dks = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(dks);
    Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
    IvParameterSpec iv = new IvParameterSpec(icv);

    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, sr);

    return cipher.doFinal(content);
  }

  private static byte[] encryptByDesCbc(byte[] content, byte[] key)
      throws GeneralSecurityException {
    return encryptByDesCbc(content, key, ZERO_VECTOR);
  }

  /**
   * @param data 数据
   * @param key 密钥
   * @param vector 初始向量
   * @throws Exception
   */
  public static byte[] calculatePBOC3DesMAC(byte[] data, byte[] key, byte[] vector)
      throws Exception {

    if (key == null || data == null) throw new RuntimeException("data or key is null.");
    if (key.length != 16) throw new RuntimeException("key length is not 16 byte.");

    byte[] leftKey = new byte[8];
    System.arraycopy(key, 0, leftKey, 0, 8);

    // 拆分数据（8字节块/Block）
    final int dataLength = data.length;
    final int blockCount = dataLength / 8 + 1;
    final int lastBlockLength = dataLength % 8;

    byte[][] dataBlock = new byte[blockCount][8];
    for (int i = 0; i < blockCount; i++) {
      int copyLength = i == blockCount - 1 ? lastBlockLength : 8;
      System.arraycopy(data, i * 8, dataBlock[i], 0, copyLength);
    }
    dataBlock[blockCount - 1][lastBlockLength] = (byte) 0x80;

    byte[] desXor = xOr(dataBlock[0], vector);
    for (int i = 1; i < blockCount; i++) {
      byte[] des = encryptByDesCbc(desXor, leftKey);
      desXor = xOr(dataBlock[i], des);
    }
    desXor = encryptBy3DesCbc(desXor, key);
    return desXor;
  }

  /**
   * @param data 数据(HEX)
   * @param key 密钥(HEX)
   * @param vector 初始向量(HEX)
   * @throws Exception
   */
  public static String calculatePBOC3DesMAC(String data, String key, String vector)
      throws Exception {
    return bytesToHexString(
        calculatePBOC3DesMAC(hexStringToByte(data), hexStringToByte(key), hexStringToByte(vector)));
  }

  /**
   * @param data 数据(HEX)
   * @param key 密钥(HEX)
   * @throws Exception
   */
  public static String calculatePBOC3DesMAC(String data, String key)
      throws Exception {
    return bytesToHexString(
        calculatePBOC3DesMAC(hexStringToByte(data), hexStringToByte(key),
            hexStringToByte(DEFAULT_VECTOR)));
  }

}
