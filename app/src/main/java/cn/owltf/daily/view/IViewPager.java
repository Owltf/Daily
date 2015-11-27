package cn.owltf.daily.view;


import java.util.List;

import cn.owltf.daily.model.entity.Daily;

public interface IViewPager {

    void loadData(List<Daily> dailies);
    void showProgress();
    void hideProgress();
}
