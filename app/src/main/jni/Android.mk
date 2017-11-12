

LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

    LOCAL_MODULE := andfix
    LOCAL_SRC_FILES:= andfix.cpp\

LOCAL_CFLAGS	:= -std=gnu++11 -fpermissive -DDEBUG -O0

LOCAL_C_INCLUDES := 

LOCAL_SHARED_LIBRARIES := 
    
LOCAL_LDLIBS    := -llog

LOCAL_STATIC_LIBRARIES := 

LOCAL_MODULE:= andfix

include $(BUILD_SHARED_LIBRARY)
