/*
 *
 * Copyright (c) 2015, alipay.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * 	andfix.cpp
 *
 *  @author : sanping.li@alipay.com
 *
 */
#include <jni.h>
#include <stdio.h>
#include "common.h"
#include <string>

#include "art/art.h"
#include "art/art_5_0.h"
//#include "com_andfixjni_demo_DxManager.h"

#ifndef _Included_com_andfixjni_demo_DxManager
#define _Included_com_andfixjni_demo_DxManager
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_andfixjni_demo_DxManager
 * Method:    replaceMethod
 * Signature: (Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V
 */
JNIEXPORT void JNICALL Java_com_andfixjni_demo_DxManager_replaceMethod
        (JNIEnv *env, jobject /* this */, jobject src, jobject dest){
    //拿到错误的class
    art::mirror::ArtMethod* smeth =
            (art::mirror::ArtMethod*) env->FromReflectedMethod(src);

    art::mirror::ArtMethod* dmeth =
            (art::mirror::ArtMethod*) env->FromReflectedMethod(dest);

    reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->class_loader_ =
            reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->class_loader_; //for plugin classloader
    reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->clinit_thread_id_ =
            reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->clinit_thread_id_;
    reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->status_ = reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->status_-1;
    //for reflection invoke
    reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->super_class_ = 0;

    smeth->declaring_class_ = dmeth->declaring_class_;
    smeth->access_flags_ = dmeth->access_flags_ | 0x0001;
    smeth->dex_cache_resolved_types_ = dmeth->dex_cache_resolved_types_;
    smeth->dex_cache_resolved_methods_ = dmeth->dex_cache_resolved_methods_;
    smeth->dex_cache_strings_ = dmeth->dex_cache_strings_;
    smeth->dex_code_item_offset_ = dmeth->dex_code_item_offset_;
    smeth->dex_method_index_ = dmeth->dex_method_index_;
    smeth->gc_map_ = dmeth->gc_map_;
    smeth->entry_point_from_jni_ = dmeth->entry_point_from_jni_;
    smeth->entry_point_from_quick_compiled_code_ = dmeth->entry_point_from_quick_compiled_code_;

    smeth->entry_point_from_interpreter_ = dmeth->entry_point_from_interpreter_;

    smeth->method_index_ = dmeth->method_index_;

    LOGD("replace_5_0: %d , %d", smeth->entry_point_from_quick_compiled_code_,
         dmeth->entry_point_from_quick_compiled_code_);

}

JNIEXPORT jstring JNICALL Java_com_andfixjni_demo_DxManager_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


#ifdef __cplusplus
}
#endif
#endif


