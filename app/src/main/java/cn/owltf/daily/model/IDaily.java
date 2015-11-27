package cn.owltf.daily.model;


import java.util.List;

import cn.owltf.daily.model.entity.Daily;

public interface IDaily {
    void getDailyResult(int date);

    void saveDailies(List<Daily> dailies, int date);

    void getGsonNews(int id);
}
