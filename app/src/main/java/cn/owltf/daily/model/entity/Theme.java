package cn.owltf.daily.model.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theme  implements Serializable{
    @SerializedName("subscribed")
    public boolean suscribed;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;
}
