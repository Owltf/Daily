package cn.owltf.daily.model;


import java.util.List;

import cn.owltf.daily.model.entity.Daily;

public interface IHttpCallBack {
    void onFinish(List<Daily> dailyList);
}
