package cn.owltf.daily.domain.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import cn.owltf.daily.domain.application.App;

public class NetworkUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, ResponseHandlerInterface responseHandlerInterface) {
        client.get(url, responseHandlerInterface);
    }

    public static void getImage(String url, ResponseHandlerInterface responseHandlerInterface) {
        client.get(url, responseHandlerInterface);
    }

    public static boolean isNetworkConnected() {
        Context context = App.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
