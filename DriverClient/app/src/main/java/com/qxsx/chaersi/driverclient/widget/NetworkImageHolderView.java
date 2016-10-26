package com.qxsx.chaersi.driverclient.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;
    private Picasso picasso;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        picasso= BaseApplication.getInstance().built;


        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        picasso.with(context)
                .load(data)
                .placeholder(R.drawable.index_banner_load)
                .error(R.drawable.index_banner_err)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("imgHolder","加载图片成功");
                    }

                    @Override
                    public void onError() {
                        Log.i("imgHolder","加载图片失败");                    }
                });

    }
}
