package cn.owltf.daily.view;


import android.os.Handler;

import cn.owltf.daily.model.entity.DailyGson;

public interface IGsonNews {

    void loadGson(DailyGson dailyGson);

    Handler getHandler();

}
