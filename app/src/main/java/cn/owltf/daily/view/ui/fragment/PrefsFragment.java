package cn.owltf.daily.view.ui.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.widget.CheckBox;

import cn.owltf.daily.R;
import cn.owltf.daily.domain.utils.PrefUtils;
import cn.owltf.daily.domain.utils.VersionUtils;
import cn.owltf.daily.presenters.WebActivityPresenter;

public class PrefsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private CheckBoxPreference mIsEnableCache;
    private Preference mCachePre, mVersionPre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        initPrefs();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        SharedPreferences.Editor editor = preference.getEditor();
        if(mIsEnableCache == preference){
            editor.putBoolean(PrefUtils.PRE_CACHE_ENABLE, mIsEnableCache.isChecked());
        }else if(mCachePre == preference){
            WebActivityPresenter presenter = new WebActivityPresenter();
            long deletedSize = presenter.clearCacheFolder();
            Snackbar.make(getView(), "释放了"+(deletedSize/1024L/1024L)+"MB", Snackbar.LENGTH_LONG).show();
        }
        return true;
    }

    private void initPrefs(){
        mIsEnableCache = (CheckBoxPreference) findPreference("enable_cache");
        mCachePre = findPreference("delete_cache");
        mCachePre.setOnPreferenceClickListener(this);
        mVersionPre = findPreference("version");
        mVersionPre.setTitle("版本:" + VersionUtils.setUpVersionName(getActivity()));
    }


}
