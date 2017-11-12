package com.andfixjni.demo.ok;

import android.util.Log;

import com.andfixjni.demo.annotation.MethodReplace;

/**
 * @author Administrator
 * @date Created on 2017/10/27
 * @description
 * 修复好的bug，放在服务端
 * 如何打包dex
 * 在build里面intermediates+classes+debug+包名找到class文件
 * 通过class打包为修复dex
 * E:\Android\sdk\build-tools\25.0.0\dx.bat 使用方法
 * 打开cmd，先cd到dx的目录
 * dx --dex --output E:\github\AndFixJniDemo\andfix-repair\out.dex E:\github\AndFixJniDemo\andfix-repair
 * 第一个参数是打包为dex，第二个参数设置输出路径+原打包文件路径
 */

public class Cat {
	//要修复的地方class名+要修复的方法
	@MethodReplace(clazz = "com.andfixjni.demo.Cat",method = "call")
	public int call(){
		int i=1;
		int j = 100;
		Log.e("Cat","妙妙妙");
		int result = j/i;
		return result;
	}
}
