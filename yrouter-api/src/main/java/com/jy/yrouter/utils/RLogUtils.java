package com.jy.yrouter.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @description
 * @date: 2020/4/21 17:42
 * @author: jy
 */
public class RLogUtils {

    public static boolean SHOW_LOG = true;
    private static final String TAG = "YRouter";

    enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public static void setEnableLog(boolean enableLog) {
        SHOW_LOG = enableLog;
    }

    public static void e(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.ERROR);
        }
    }

    public static void i(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.INFO);
        }
    }

    public static void w(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.WARN);
        }
    }

    public static void d(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.DEBUG);
        }
    }

    //**************************format**********************************
    public static void eFormat(String msgFormat, Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String message = String.format(msgFormat, args);
            log(TAG, traceElement, message, LogLevel.ERROR);
        }
    }

    public static void iFormat(String msgFormat, Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String message = String.format(msgFormat, args);
            log(TAG, traceElement, message, LogLevel.INFO);
        }
    }

    public static void wFormat(String msgFormat, Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String message = String.format(msgFormat, args);
            log(TAG, traceElement, message, LogLevel.WARN);
        }
    }

    public static void dFormat(String msgFormat, Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String message = String.format(msgFormat, args);
            log(TAG, traceElement, message, LogLevel.DEBUG);
        }
    }

    private static String getString(Object... args) {
        if (args.length == 1) {
            return args[0].toString();
        } else {
            StringBuilder message = new StringBuilder();
            for (Object object : args) {
                message.append(object);
                message.append("---");
            }
            return message.toString();
        }
    }

    private static void log(String tag, StackTraceElement traceElement, String message, LogLevel logLevel) {
        String msgFormat = "[ %s %s ]---类名：%s---方法名：%s---第%d行---信息---%s";
        String messageWithTime = String.format(Locale.CHINA
                , msgFormat
                , new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINA).format(new Date())
                , logLevel.name()
                , traceElement.getFileName()
                , traceElement.getMethodName()
                , traceElement.getLineNumber()
                , message);
        switch (logLevel) {
            case INFO:
                Log.i(tag, messageWithTime);
                break;
            case WARN:
                Log.w(tag, messageWithTime);
                break;
            case DEBUG:
                Log.d(tag, messageWithTime);
                break;
            case ERROR:
                Log.e(tag, messageWithTime);
                break;
        }
    }

}
