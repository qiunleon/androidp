//package com.evideo.service.manager;
//
//public class RecordManager {
//
//    public native int native_start(String streamUrl, String savePath);
//
//    public native int native_stop();
//
//    static {
//        System.loadLibrary("avutil-55");
//        System.loadLibrary("swresample-2");
//        System.loadLibrary("avcodec-57");
//        System.loadLibrary("avformat-57");
//        System.loadLibrary("swscale-4");
//        System.loadLibrary("avfilter-6");
//        System.loadLibrary("FFmpegRecordUtils");
//    }
//}