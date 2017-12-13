package com.brightcns.liangla.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by 巩贺 on 2017/4/20.
 *
 *  2010030 token 错误从新登陆
 */

public class CheckErrorCode {
    public static void checkErrorCodeAndToast(Context context, int code){
        if (code == 101010) {
            ToastUtils.showShort(context, "code认证错误");
            Log.e("TAG", "101010code认证错误");
        } else if (code == 101011) {
            ToastUtils.showShort(context, "sign错误");
            Log.e("TAG", "101011sign错误");
        } else if (code == 101012) {
            ToastUtils.showShort(context, "请求超时");
            Log.e("TAG", "101012请求超时");
        } else if (code == 101013) {
            ToastUtils.showShort(context, "客户机token验证错误");
            Log.e("TAG", "101013客户机token验证错误");
        } else if (code == 2010030) {
            ToastUtils.showShort(context, "用户token验证错误");
            Log.e("TAG", "101014用户token验证错误");
        }else if (code == 101015) {
            ToastUtils.showShort(context, "客户机已被拉黑");
            Log.e("TAG", "101015code客户机已被拉黑");
        }else if (code == 101016) {
            ToastUtils.showShort(context, "手机号已被拉黑");
            Log.e("TAG", "101016手机号已被拉黑");
        } else if (code == 101017) {
            ToastUtils.showShort(context, "用户已在其他机器登录，请退出后再登录");
            Log.e("TAG", "101017用户已在其他机器登录，请退出后再登录");
        }else if (code == 101110) {
            ToastUtils.showShort(context, "禁止一分钟内连续发送");
            Log.e("TAG", "101110禁止一分钟内连续发送");
        } else if (code == 101112) {
            ToastUtils.showShort(context, "验证码已失效");
            Log.e("TAG", "101112验证码已失效");
        }else if (code == 101113) {
            ToastUtils.showShort(context, "验证码错误");
            Log.e("TAG", "101113验证码错误");
        }else if (code == 101114) {
            ToastUtils.showShort(context, "超过发送的次数过多，手机已被锁定");
            Log.e("TAG", "101114超过发送的次数过多，手机已被锁定");
        }else if (code == 101210) {
            ToastUtils.showShort(context, "加密失败");
            Log.e("TAG", "101210加密失败");
        }else if (code == 101211) {
            ToastUtils.showShort(context, "解密失败");
            Log.e("TAG", "101211解密失败");
        } else if (code == 101316) {
            ToastUtils.showShort(context, "账户余额不足");
            Log.e("TAG", "账户余额不足");
        } else if (code == 201008) {
            ToastUtils.showShort(context, "请先验证手机和身份证号码");
            Log.e("TAG", "请先验证手机和身份证号码");
        } else if (code == 201009) {
            ToastUtils.showShort(context, "参数请求非法");
            Log.e("TAG", "参数请求非法");
        } else if (code == 2010010) {
            ToastUtils.showShort(context, "手机号不合法");
            Log.e("TAG", "手机号不合法");
        } else if (code == 2010011) {
            ToastUtils.showShort(context, "密码验证失败");
            Log.e("TAG", "密码验证失败");
        } else if (code == 2010012) {
            ToastUtils.showShort(context, "密码未初始化");
            Log.e("TAG", "密码未初始化");
        } else if (code == 2010013) {
            ToastUtils.showShort(context, "密码已初始化");
            Log.e("TAG", "密码已初始化");
        } else if (code == 201010) {
            ToastUtils.showShort(context, "非法客户机");
            Log.e("TAG", "201010非法客户机");
        }else if (code == 201011) {
            ToastUtils.showShort(context, "手机号已存在");
            Log.e("TAG", "201011手机号已存在");
        }else if (code == 201012) {
            ToastUtils.showShort(context, "手机号不存在");
            Log.e("TAG", "201012手机号不存在");
        }else if (code == 201013) {
            ToastUtils.showShort(context, "密码错误");
            Log.e("TAG", "201013code密码错误");
        }else if (code == 201014) {
            ToastUtils.showShort(context, "上传图片失败");
            Log.e("TAG", "201014上传图片失败");
        }else if (code == 301010) {
            ToastUtils.showShort(context, "参数不能为空");
            Log.e("TAG", "301010参数不能为空");
        }else if (code == 301011) {
            ToastUtils.showShort(context, "手机号不合法");
            Log.e("TAG", "301011手机号不合法");
        }else if (code == 401010) {
            ToastUtils.showShort(context, "账户已存在");
            Log.e("TAG", "401010账户已存在");
        }else if (code == 401011) {
            ToastUtils.showShort(context, "账户不存在");
            Log.e("TAG", "401011账户不存在");
        }else if (code == 501010) {
            ToastUtils.showShort(context, "用户基本信息不存在");
            Log.e("TAG", "501010用户基本信息不存在");
        }else if (code == 501011) {
            ToastUtils.showShort(context, "用户个性化信息不存在");
            Log.e("TAG", "501011用户个性化信息不存在");
        }else if (code == 501012) {
            ToastUtils.showShort(context, "用户账户信息不存在");
            Log.e("TAG", "501012用户账户信息不存在");
        }else if (code == 501013) {
            ToastUtils.showShort(context, "用户金融信息不存在");
            Log.e("TAG", "501013用户金融信息不存在");
        }else if (code==999){
            ToastUtils.showShort(context, "用户板块错误，无法进行登录等操作");
            Log.e("TAG", "999用户板块错误，无法进行登录等操作");
        }else if (code==900002){
            ToastUtils.showShort(context, "用户积分不足");
            Log.e("TAG", "用户积分不足");
        }
    }

}
