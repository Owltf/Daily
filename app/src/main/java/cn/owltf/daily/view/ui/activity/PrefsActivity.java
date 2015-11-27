package cn.owltf.daily.view.ui.activity;


import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import cn.owltf.daily.R;
import cn.owltf.daily.domain.application.App;
import cn.owltf.daily.domain.utils.StatusBarUtils;
import cn.owltf.daily.view.ui.activity.base.SwipeBackActivity;
import cn.owltf.daily.view.ui.fragment.PrefsFragment;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class PrefsActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_prefs;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
        ButterKnife.bind(this);
        setTitle(getString(R.string.title_setting));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame, new PrefsFragment())
                .commit();
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
}