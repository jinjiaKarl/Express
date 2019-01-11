package cn.edu.njupt.iot.b16070520.express.utils;


import android.content.Context;
import android.content.SharedPreferences;

//封装SharedPreferences
public class ShareUtils {
    public static final String NAME = "config";

    /*
     * 1.根据Context获取SharedPreferences对象
     * 2.利用edit()方法获取Editor对象
     * 3.通过Editor对象存储key-value键值对数据
     * 4.通过commit()方法提交数据
     */

    //键 值
    public static void putString(Context mContext, String key, String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        // SharedPreferences.Editor editor = sp.edit();
        sp.edit().putString(key,value).commit();
    }

    //键 默认值
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        // defValue是默认值
        return sp.getString(key,defValue);
    }

    //键 值
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    //键 默认值
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    //键 值
    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    //键 默认值
    public static boolean getBoolean(Context mContext,String key,boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    //刪除 单个
    public static void deleShare(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //刪除 全部
    public static void deleAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
