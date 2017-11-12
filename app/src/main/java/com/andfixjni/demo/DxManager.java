package com.andfixjni.demo;

import android.content.Context;
import android.util.Log;

import com.andfixjni.demo.annotation.MethodReplace;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;

/**
 * @author Administrator
 * @date Created on 2017/10/27
 * @description
 */

public class DxManager {
	private Context context;

	public DxManager(Context context) {
		this.context = context;
	}

	public void loadDex(File dexFilePath){
		File optFile = new File(context.getCacheDir(),dexFilePath.getName());
		if(optFile.exists()){
			optFile.delete();
		}
		//加载Dex文件
		try {
			DexFile dexFile = DexFile.loadDex(dexFilePath.getAbsolutePath(),optFile.getAbsolutePath(), Context.MODE_PRIVATE);

			//遍历dex的class文件
			Enumeration<String> entry = dexFile.entries();
			Class<?> clazz = null;
			while (entry.hasMoreElements()){
				String className = entry.nextElement();
				//修复好的类
				clazz = dexFile.loadClass(className, context.getClassLoader());
				if (clazz != null) {
					fixClass(clazz);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fixClass(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			//拿到要修复的注解
			MethodReplace methodReplace = method.getAnnotation(MethodReplace.class);
			if (methodReplace == null)
				continue;
			String wrongClazzName = methodReplace.clazz();
			String wrongMethodName = methodReplace.method();
			if (!isEmpty(wrongClazzName) && !isEmpty(wrongMethodName)) {
				replaceMethod(wrongClazzName, wrongMethodName, method);
			}
		}
	}

	private void replaceMethod(String wrongClazzName, String wrongMethodName, Method rightMethod) {
		try {
			Class wrongClass = Class.forName(wrongClazzName);
			//最终拿到错误的method对象
			Method wrongMethod = wrongClass.getMethod(wrongMethodName,rightMethod.getParameterTypes());
			//走jni层去进行方法替换修复
			replaceMethod(wrongMethod,rightMethod);
		} catch (Exception e) {
			Log.e(TAG, "replaceMethod", e);
		}
	}

	static {
		//加载库文件
		System.loadLibrary("andfix");
	}

	public native void replaceMethod(Method wrongMethodName, Method rightMethod);

	public native String stringFromJNI();
}
