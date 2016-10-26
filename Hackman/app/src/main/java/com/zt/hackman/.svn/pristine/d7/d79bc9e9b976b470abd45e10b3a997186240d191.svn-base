package com.zt.hackman.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.zt.hackman.R;
import com.zt.hackman.widget.SimpleFooter;
import com.zt.hackman.widget.SimpleHeader;
import com.zt.hackman.widget.ZrcListView;

/**
 * Created by Administrator on 2016/9/23.
 */
public class SystemUtils {
    public static void call(String a, Activity activity){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + a);
        intent.setData(data);
        activity.startActivity(intent);
    }

    /**
     * 初始化ListView
     * @param ac
     * @param listView
     */
    public static void initListView(Context ac, ZrcListView listView){
        SimpleHeader header = new SimpleHeader(ac);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(ac);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
    }
}
