package cn.owltf.daily.model.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DailyGson implements Serializable{
    @SerializedName("body")
    public String body;

    @SerializedName("image_source")
    public String image_source;

    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("share_url")
    public String share_url;

    @SerializedName("ga_prefix")
    public int ga_prefix;

    @SerializedName("type")
    public int type;

    @SerializedName("id")
    public int id;
}
