package cn.owltf.daily.model.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DailyResult implements Serializable{

    @SerializedName("date")
    public int date;

    @SerializedName("stories")
    public List<Daily> stories;
}
