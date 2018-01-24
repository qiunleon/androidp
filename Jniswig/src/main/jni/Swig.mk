# 检查SWIG_PACKAGE是否已经定义
ifndef SWIG_PACKAGE
    $(error SWIG_PACKAGE is not defined.)
endif


# "/"替换java目录中的"."
SWIG_OUT_DIR := src\main\java\$(subst .,/,$(SWIG_PACKAGE))


# 设置SWIG模式
ifndef SWIG_TYPE
    SWIG_TYPE := c
endif
ifeq ($(SWIG_TYPE),cxx)
    SWIG_MODE := -c++
else
    SWIG_MODE:=
endif


# 追加SWIG封装源文件
LOCAL_SRC_FILES += $(foreach SWIG_INTERFACE, $(SWIG_INTERFACES), $(basename $(SWIG_INTERFACE)))_wrap.$(SWIG_TYPE)


# 添加.cxx作为c++文件扩展名
LOCAL_CPP_EXTENSION += .cxx


# 生成SWIG封代码
%_wrap.$(SWIG_TYPE):%.i
	# $(call host-mkdir, $(SWIG_OUT_DIR))
	swig -java $(MY_SWIG_MODE) -package $(SWIG_PACKAGE) -outdir $(SWIG_OUT_DIR) $<