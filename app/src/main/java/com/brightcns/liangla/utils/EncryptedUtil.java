package com.brightcns.liangla.utils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 请求加密
 * Created by Tech-605 on 2017/11/11.
 */

public class EncryptedUtil {

    private static final String KEY = "532c28d5412dd75bf975fb951c740a30";
    private Map<String, Object> paramsMap;
    private String signKey;

    private EncryptedUtil(){

    }

    private EncryptedUtil(EncryptedUtil builder, String signKey){
        this.paramsMap = builder.paramsMap;
        if (!StringUtils.isEmpty(signKey)){
            this.signKey = signKey;
        }else {
            this.signKey = KEY;
        }
    }

    private List<MultipartBody.Part> getParts(){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        String[] keyArray = new String[0];
        keyArray = paramsMap.keySet().toArray(keyArray);
        // 排序key
        keyArray = sortOfString(keyArray);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keyArray.length; i++) {
            String key = keyArray[i];
            Object value = paramsMap.get(key);
            try {
                if (value == null) {
                    continue;
                    // value = URLEncoder.encode(value, "UTF-8");
                }
                // 拼接加密字符串
                boolean isEncode = false;
                builder.addFormDataPart(key,isEncode ? URLEncoder.encode(String.valueOf(value), "UTF-8") : String.valueOf(value));

                // 拼接加密字符串
                sb.append(key);
                sb.append("=");
                sb.append(isEncode ? URLEncoder.encode(String.valueOf(value), "UTF-8") : value);
                sb.append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        builder.addFormDataPart("mdkey",toMd5(sb.toString()));
        return builder.build().parts();

    }
    /**
     * 请求参数加密
     *
     * @return 返回根据参数和key组合返回的加密串
     */
    private String getMdKey() {
        String[] keyArray = new String[0];
        keyArray = paramsMap.keySet().toArray(keyArray);
        // 排序key
        keyArray = sortOfString(keyArray);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keyArray.length; i++) {
            String key = keyArray[i];
            Object value = paramsMap.get(key);
            try {
                if (value == null) {
                    continue;
                    // value = URLEncoder.encode(value, "UTF-8");
                }
                // 拼接加密字符串
                sb.append(key);
                sb.append("=");
                boolean isEncode = false;
//
                sb.append(isEncode ? URLEncoder.encode(String.valueOf(value), "UTF-8") : value);
                sb.append("&");
                // put请求参数
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return toMd5(sb.toString());
    }

    /**
     * 将等式字符串按等式左边字符串升序排序
     *
     * @param strings
     * @return 排序后的字符串
     * @throws Exception
     */
    private String[] sortOfString(String[] strings) {
        int len = strings.length;
        for (int i = 0; i <= len - 1; i++) {
            for (int j = i + 1; j <= len - 1; j++) {
                if (compareString(strings[i], strings[j]) > 0) {
                    String temp = strings[j];
                    strings[j] = strings[i];
                    strings[i] = temp;
                }
            }
        }
        return strings;
    }

    /**
     * 比较两个字母大小
     *
     * @param one
     * @param two
     * @return one>two则为正,one==two则为0,one<two则为负
     */
    private int compareString(String one, String two) {
        // 转换为小写
//        one = one.toLowerCase(Locale.getDefault());
//        two = two.toLowerCase(Locale.getDefault());
        int oneLen = one.length();
        int twoLen = two.length();
        // 填充至相等位数
        if (oneLen > twoLen) {
            StringBuffer sb = new StringBuffer(two);
            for (int i = 0; i < oneLen - twoLen; i++) {
                sb.append((char) 0);
            }
            two = sb.toString();
        } else if (oneLen < twoLen) {
            StringBuffer sb = new StringBuffer(one);
            for (int i = 0; i < twoLen - oneLen; i++) {
                sb.append((char) 0);
            }
            one = sb.toString();
        }
        // 每一位字符比较
        for (int i = 0; i < one.length() && i < two.length(); i++) {
            char oneChar = one.charAt(i);
            char twoChar = two.charAt(i);
            int diff = (int) oneChar - (int) twoChar;
            if (diff == 0) {
                continue;
            } else {
                return diff;
            }
        }
        return 0;
    }

    private String toMd5(String paramsString){
        try {
            // 去掉最后多余&
            paramsString = paramsString.substring(0, paramsString.length() - 1);
            paramsString += signKey;
            // 加密
            String mdkey = md5(paramsString);
            // put mdkey
            LogUtils.i("EncryptedUtil -- params",paramsString);
            LogUtils.i("EncryptedUtil -- mdkey",mdkey);
            return mdkey;
        }catch (Exception e){
            LogUtils.e("EncryptedUtil",e.getMessage());
            return null;
        }
    }

    private  String md5(String txt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(txt.getBytes("UTF-8"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
            StringBuffer buf = new StringBuffer();
            for (byte b : md.digest()) {
                buf.append(String.format("%02x", b & 0xff));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static Builder with(){
        return new Builder();
    }


    public static class Builder {

        private EncryptedUtil target;
        private EncryptedUtil encryptedUtil;
        private String signKey;

        private Builder() {
            target = new EncryptedUtil();
        }

        public Builder setParams(String key,Object val){
            if (target.paramsMap == null){
                target.paramsMap = new HashMap<>();
            }
            target.paramsMap.put(key,val);
            return this;
        }

        public Builder setSignKey(String signKey){
            this.signKey = signKey;
            return this;
        }

        public Builder build(){
            if (encryptedUtil == null){
                encryptedUtil = new EncryptedUtil(target,signKey);
            }
            return this;
        }

        public String mdkey(){
            build();
            return encryptedUtil.getMdKey();
        }

        public List<MultipartBody.Part> parts(){
            build();
            return encryptedUtil.getParts();
        }
    }
}
