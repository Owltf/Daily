package cn.owltf.daily.view.ui.activity;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.owltf.daily.R;
import cn.owltf.daily.domain.application.App;
import cn.owltf.daily.domain.utils.IntentKeys;
import cn.owltf.daily.domain.utils.PrefUtils;
import cn.owltf.daily.domain.utils.ShareUtils;
import cn.owltf.daily.domain.utils.StatusBarUtils;
import cn.owltf.daily.domain.utils.ThemeUtils;
import cn.owltf.daily.presenters.WebActivityPresenter;
import cn.owltf.daily.view.IWebView;
import cn.owltf.daily.view.ui.activity.base.SwipeBackActivity;
import cn.owltf.daily.view.widget.GlidePaletteListenerImp;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class WebActivity extends SwipeBackActivity implements IWebView {

    public static final String TAG = "WebActivity";

    private SwipeBackLayout mSwipeBackLayout;
    private WebActivityPresenter mPresenter;
    private GlidePaletteListenerImp mPaletteListenerImp;

    private String mUrl;

    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.news_header)
    ImageView mHeaderImg;
    @Bind(R.id.img_source)
    TextView mHeaderSource;
    @Bind(R.id.nsv_content)
    NestedScrollView mNestedScrollView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new WebActivityPresenter(this);
        ButterKnife.bind(this);
        App.addActivity(this);
//        fabProgressCircle.setVisibility(View.INVISIBLE);
        ThemeUtils.changeTheme(this);
        if (!ThemeUtils.isLight) {
            mNestedScrollView.setBackgroundColor(getResources().getColor(R.color.window_background_dark));
        }
        mPaletteListenerImp = new GlidePaletteListenerImp(mHeaderImg, this, mToolbarLayout);
        mUrl = getIntent().getStringExtra(IntentKeys.EXTRA_URL);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (PrefUtils.isEnableCache()) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDatabaseEnabled(true);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mPresenter.getBetterHtml(mUrl);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_share:
                ShareUtils.share(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) mWebView.destroy();
        App.removeActivity(this);
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        if (mWebView != null) mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    @Override
    public File getWebViewCacheDir() {
        return this.getCacheDir();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadBetterHtml(Map<String, String> htmlMap) {
        UIAsyncTask uiAsyncTask = new UIAsyncTask();
        uiAsyncTask.execute(htmlMap);
    }




    private class UIAsyncTask extends AsyncTask<Map<String, String>, Map.Entry<String, String>, Void> {

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Map<String, String>... params) {
            Map<String, String> map = params[0];
            for (Map.Entry<String, String> entry : map.entrySet()) {
                publishProgress(entry);
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onProgressUpdate(Map.Entry<String, String>... values) {
            Map.Entry<String, String> entry = values[0];
            if (entry.getKey().equals("headline_title")) {
                mToolbarLayout.setTitle(entry.getValue());
            } else if (entry.getKey().equals("content")) {
//                Log.d(TAG, entry.getValue());
                mWebView.loadDataWithBaseURL(mUrl, entry.getValue(), "text/html", "uft-8", null);
            } else if (entry.getKey().equals("img")) {
                if (ThemeUtils.isLight) {
                    Glide.with(App.getContext()).load(entry.getValue()).asBitmap().centerCrop().listener(mPaletteListenerImp).into(mHeaderImg);
                } else {
                    Glide.with(App.getContext()).load(entry.getValue()).asBitmap().centerCrop().into(mHeaderImg);
                }
            } else if (entry.getKey().equals("img_source")) {
                mHeaderSource.setText(entry.getValue());
                mHeaderSource.setVisibility(View.VISIBLE);
            }
        }
    }
}
