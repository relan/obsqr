# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


LOCAL_PATH := $(call my-dir)
#APP_PATH:= $(NDK)/$(call my-dir) 

include $(CLEAR_VARS) 

LOCAL_MODULE := libiconv 

LIBICONV := libiconv

LOCAL_CFLAGS := -I$(LOCAL_PATH)/$(LIBICONV)
LOCAL_SRC_FILES := $(LIBICONV)/iconv.c

include $(BUILD_STATIC_LIBRARY) 

#LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := zbar

LOCAL_SRC_FILES := convert.c decoder.c error.c image.c img_scanner.c \
	refcnt.c scanner.c symbol.c video.c window.c \
	\
	bch15_5.c binarize.c isaac.c qrdec.c qrdectxt.c rs.c util.c \
	\
	processor/null.c video/null.c window/null.c decoder/qr_finder.c \
	trikita_zbar.c
	
LOCAL_CFLAGS := -I$(LOCAL_PATH) -I$(LOCAL_PATH)/$(LIBICONV)
LOCAL_LDLIBS := -llog

LOCAL_STATIC_LIBRARIES := libiconv

include $(BUILD_SHARED_LIBRARY)

