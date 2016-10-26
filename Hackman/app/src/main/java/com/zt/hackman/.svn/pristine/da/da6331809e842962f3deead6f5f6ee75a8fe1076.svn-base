package com.zt.hackman.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zt.hackman.R;


@SuppressLint("NewApi")
public class MainContentLayout extends LinearLayout {

	private Context context;

	private LinearLayout mainLinearLayout,// 主布局
			allLinearLayout;// 父布局

//	public LinearLayout errorView;
//	public LinearLayout loadingLayout;
//	public TextView txtErrorTip;
//	public Button btnRetry;
	//public ProgressActivity progressActivity;




	public MainContentLayout(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public MainContentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	@SuppressLint("NewApi")
	private void init() {
		// TODO Auto-generated method stub
		if (null == getBackground()) {
			setBackgroundColor(Color.parseColor("#eeeef4"));
		}
		// 导入布局并实例化
		LayoutInflater.from(context).inflate(R.layout.main_content_layout,
				this, true);

		allLinearLayout = (LinearLayout) findViewById(R.id.ll_all);
		mainLinearLayout = (LinearLayout)findViewById(R.id.root_view);
	}


	public LinearLayout getMainLinearLayout() {
		return mainLinearLayout;
	}

	public LinearLayout getAllLinearLayout() {
		return allLinearLayout;
	}

	public void setMainLinearLayout(LinearLayout mainLinearLayout) {
		this.mainLinearLayout = mainLinearLayout;
	}


}
