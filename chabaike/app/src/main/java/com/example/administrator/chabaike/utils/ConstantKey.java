package com.example.administrator.chabaike.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class ConstantKey {
    public static final  String PRE_KEY_FIRST_OPEN="pre_key_first_open";
    public static final String DBPATH= Environment.getExternalStorageDirectory()+ File.separator+"du.db";
    public static final String DETAILPATH= Environment.getExternalStorageDirectory()+ File.separator+"detail.db";
    public static final String SUGGESTPATH= Environment.getExternalStorageDirectory()+ File.separator+"suggest.db";
    public static final  String CONTENT_DETAIL="http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=";
}
