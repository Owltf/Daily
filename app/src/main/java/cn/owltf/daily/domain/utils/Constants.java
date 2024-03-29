package cn.owltf.daily.domain.utils;


import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {


    public static final int PAGE_COUNT = 7;

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator + "Daily";
    public static final String LOG_DIR = ROOT_DIR + File.separator + "log";
    public static final String DOWNLOAD_DIR = ROOT_DIR + File.separator + "download";
    public static final String CACHE_DIR = ROOT_DIR + File.separator + "cache";

    public static final String LOG_NAME = "crash.log";
}
