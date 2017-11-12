package com.andfixjni.demo;

import android.util.Log;

/**
 * @author Administrator
 * @date Created on 2017/10/27
 * @description
 * 已经上线出错的代码
 */

public class Cat {
	public int call(){
		//随便弄个错误
		int i=0;
		int j = 100;
		Log.e("Cat","汪汪汪");
		int result = j/i;
		return result;
	}
}
