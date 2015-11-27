package cn.owltf.daily.domain.utils;


import android.content.Context;
import android.content.Intent;

import cn.owltf.daily.R;

public class ShareUtils {

    public static void share(Context context){
        share(context, context.getString(R.string.about_share_text));
    }

    public static void share(Context context, String extraText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.about_share_text));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.about_share_text)));
    }
}
