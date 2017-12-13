package com.brightcns.liangla.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import com.brightcns.liangla.app.MyApplication;

/**
 * SP缓存工具类
 */
public final class PreferenceUtil {

  public static void reset(final Context context) {

    Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
    edit.clear();
    edit.apply();
  }


  public static String getString(String key, String defValue) {

    return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication())
            .getString(key, defValue);
  }


  public static long getLong(String key, long defValue) {

    return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication())
            .getLong(key, defValue);
  }


  public static float getFloat(String key, float defValue) {

    return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication())
            .getFloat(key, defValue);
  }

  public static <E extends Serializable> List<E> getList(String key) {
    try {
      return (List<E>) getListStr(key);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**获取对象*/
  private static Object getListStr(String key)
          throws IOException, ClassNotFoundException
  {
    String wordBase64 = getString(key,"");
    // 将base64格式字符串还原成byte数组
    if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
      return null;
    }
    byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
    ByteArrayInputStream bais     = new ByteArrayInputStream(objBytes);
    ObjectInputStream ois      = new ObjectInputStream(bais);
    // 将byte数组转换成product对象
    Object obj = ois.readObject();
    bais.close();
    ois.close();
    return obj;
  }




  public static void put(String key, String value) {

    putString(key, value);
  }


  public static void put(String key, int value) {

    putInt(key, value);
  }


  public static void put(String key, float value) {

    putFloat(key, value);
  }


  public static void put(String key, boolean value) {

    putBoolean(key, value);
  }

  public static void put(String key, List<? extends Serializable> list) {

    try {
      putList(key, list);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static void putFloat(String key, float value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    Editor editor = sharedPreferences.edit();
    editor.putFloat(key, value);
    editor.apply();
  }


  public static SharedPreferences getPreferences() {

    return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
  }


  public static int getInt(String key, int defValue) {

    return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication())
            .getInt(key, defValue);
  }


  public static boolean getBoolean(String key, boolean defValue) {

    return PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication())
            .getBoolean(key, defValue);
  }


  public static void putStringProcess(String key, String value) {

    SharedPreferences sharedPreferences = MyApplication.getApplication()
            .getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
    Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }


  public static String getStringProcess(String key, String defValue) {

    SharedPreferences sharedPreferences = MyApplication.getApplication()
            .getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
    return sharedPreferences.getString(key, defValue);
  }


  public static boolean hasString(String key) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    return sharedPreferences.contains(key);
  }


  private static void putString(String key, String value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }


  public static void putLong(String key, long value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    Editor editor = sharedPreferences.edit();
    editor.putLong(key, value);
    editor.apply();
  }


  public static void putBoolean(String key, boolean value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    Editor editor = sharedPreferences.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  public static void putList(String key, Object obj)
          throws IOException, ClassNotFoundException {
    if (obj == null) {//判断对象是否为空
      return;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = null;
    oos = new ObjectOutputStream(baos);
    oos.writeObject(obj);
    // 将对象放到OutputStream中
    // 将对象转换成byte数组，并将其进行base64编码
    String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
    baos.close();
    oos.close();

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    Editor editor = sharedPreferences.edit();
    editor.putString(key, objectStr);
    editor.apply();
  }


  private static void putInt(String key, int value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            MyApplication.getApplication());
    Editor editor = sharedPreferences.edit();
    editor.putInt(key, value);
    editor.apply();
  }


  public static void remove(String... keys) {

    if (keys != null) {
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
              MyApplication.getApplication());
      Editor editor = sharedPreferences.edit();
      for (String key : keys) {
        editor.remove(key);
      }
      editor.apply();
    }
  }

}
