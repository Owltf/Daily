package cn.owltf.daily.view;


import java.io.File;
import java.util.Map;

public interface IWebView {

    File getWebViewCacheDir();
    
    void loadBetterHtml(Map<String, String> htmlMap);
}
