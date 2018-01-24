LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# 自动生成
LOCAL_MODULE    := CalculateGCD
LOCAL_SRC_FILES := CalculateGcd.c
SWIG_PACKAGE    := com.example.swig.jni
SWIG_INTERFACES := CalculateGcd.i
SWIG_TYPE       := c
include $(LOCAL_PATH)/Swig.mk

# 调用gradlew :Jniswig:swig任务手动生成文件后Make Module
# LOCAL_MODULE    := CalculateGCD
# LOCAL_SRC_FILES := CalculateGcd_wrap.c CalculateGcd.c

include $(BUILD_SHARED_LIBRARY)