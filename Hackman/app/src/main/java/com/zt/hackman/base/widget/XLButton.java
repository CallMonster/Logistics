package com.zt.hackman.base.widget;

import android.R.anim;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

import com.zt.hackman.utils.DisplayUtil;


/**
 * 导航栏菜单按钮
 * 
 * @author Shinsoft
 *
 */
public class XLButton extends Button {
	private Context context;
	private static int xl_lr_padding = 2;// 左右边距
	private static int xl_drawable_bounds = 20;// 设置drawable 大小
	private static int xl_drawable_text_padding = -3;// 图文间距
	private static int xl_left_padding = 2;// 设置左边距
	private static int xl_right_padding = -4;// 设置右边距

	public XLButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// super(context, null, 1);
		this.context = context;
		init();
	}

	public XLButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// super(context, null, 1);
		this.context = context;
		init();
	}

	public XLButton(Context context) {
		super(context);
		// super(context, null, 1);
		this.context = context;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		// //设置图文边距
		// int drawablePadding = DisplayUtil.dip2px(context, 0);
		// setCompoundDrawablePadding(drawablePadding);
		// 设置透明背景
		// setBackgroundColor(Color.TRANSPARENT);
		// 设置边距
		int padding = DisplayUtil.dip2px(xl_lr_padding);
		setPadding(padding, 0, padding, 0);
		// 设置文本位置
		setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

		// setCompoundDrawablesWithIntrinsicBounds1(getResources().getDrawable(android.R.drawable.ic_delete),null,null,null);
	}

	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
			Drawable top, Drawable right, Drawable bottom) {
		// TODO Auto-generated method stub
		super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		if (left != null) {
			// 设置图片尺寸
			int mDrawableSize = DisplayUtil.dip2px(
					xl_drawable_bounds);
			left.setBounds(0, 0, mDrawableSize, mDrawableSize);
			if (!TextUtils.isEmpty(getText())) {
				// 设置图文边距
				int drawablePadding = DisplayUtil.dip2px(
						xl_drawable_text_padding);
				setCompoundDrawablePadding(drawablePadding);

				int padding = DisplayUtil.dip2px( xl_left_padding);
				int padding1 = DisplayUtil.dip2px(
						xl_right_padding);
				setPadding(padding1, 0, padding, 0);

			}
			setCompoundDrawables(left, top, right, bottom);
		}
	}

	public void setCompoundDrawablesWithIntrinsicBounds1(Drawable left,
			Drawable top, Drawable right, Drawable bottom) {

		// 设置图片大小
		int mDrawableSize = DisplayUtil.dip2px( xl_drawable_bounds);
		if (left != null) {

			if (!TextUtils.isEmpty(getText())) {
				left.setBounds(0, 0, mDrawableSize, mDrawableSize);
				// 设置图文边距
				int drawablePadding = DisplayUtil.dip2px(
						xl_drawable_text_padding);
				setCompoundDrawablePadding(drawablePadding);

				// 设置边距
				int padding = DisplayUtil.dip2px( xl_left_padding);
				int padding1 = DisplayUtil.dip2px(
						xl_right_padding);
				setPadding(padding1, 0, padding, 0);
			} else {
				left.setBounds(0, 0, mDrawableSize, mDrawableSize);
			}

		}
		if (right != null) {
			right.setBounds(0, 0, mDrawableSize, mDrawableSize);
		}
		if (top != null) {
			top.setBounds(0, 0, mDrawableSize, mDrawableSize);
		}
		if (bottom != null) {
			bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
		}
		setCompoundDrawables(left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable[] drawables = getCompoundDrawables();
		if (drawables != null) {
			Drawable drawableLeft = drawables[0];
			if (drawableLeft != null) {
				// final float textWidth =
				// getPaint().measureText(getText().toString());
				// final int drawablePadding = getCompoundDrawablePadding();
				// final int drawableWidth = drawableLeft.getIntrinsicWidth();
				// final float bodyWidth = textWidth + drawableWidth +
				// drawablePadding;
				// canvas.translate((getWidth() - bodyWidth) / 2, 0);

				final float textWidth = getPaint().measureText(
						getText().toString());
				// final int drawablePadding = getCompoundDrawablePadding();
				// final int drawableWidth = drawableLeft.getIntrinsicWidth();
				// 如果图片的宽高和文字字体的大小有变更则使用下列方法
				int drawablePadding = DisplayUtil.dip2px(
						xl_drawable_text_padding);// 6
				int drawableWidth = 0;
				drawableWidth = DisplayUtil.dip2px( xl_drawable_bounds);// 39

				final float bodyWidth = textWidth + drawableWidth
						+ drawablePadding;
				canvas.translate(0, 0);
			}
		}
		super.onDraw(canvas);
	}

	public void setDrawable(int drawableID){
		Drawable drawable = getResources().getDrawable(drawableID);
		this.setCompoundDrawablesWithIntrinsicBounds1(drawable,
				null, null, null);
	}
}
