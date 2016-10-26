package com.tj.opensrc.selectphoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class SystemAlbumPickerActivity_Adapter extends BaseAdapter {

	private LayoutParams mImageViewLayoutParams;
	private int mItemHeight;
	private Context context;
	protected List<?> mList;
	protected int defaultImageDrawable = R.drawable.img_loading_bg;
	protected int defaultImageViewId = R.id.imgThun;
	
	protected SystemAlbumPickerActivity_SyncPhotoLoader syncImageLoader;
	protected AbsListView mListView;

	OnImageLoadListener mImageLoadListener = new OnImageLoadListener(defaultImageDrawable, defaultImageViewId);
	
	public SystemAlbumPickerActivity_Adapter(Context context, AbsListView listView, List<SystemAlbumImageObjectVO> list) {
		this.context = context;
		this.mList = list;
		this.mListView = listView;
		mImageLoadListener = new OnImageLoadListener(listView, defaultImageDrawable, defaultImageViewId);
		syncImageLoader = new SystemAlbumPickerActivity_SyncPhotoLoader();
		mListView.setOnScrollListener(onScrollListener);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_system_album_picker, null);
			holder.imgThumbnail = (ImageView) convertView.findViewById(R.id.imgThun);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		int image_position = position-1;
		if(position == 0){
			if (holder.imgThumbnail.getLayoutParams().height != mItemHeight) {
				if (mImageViewLayoutParams != null) {
					holder.imgThumbnail.setLayoutParams(mImageViewLayoutParams);
				}
			}
			holder.imgThumbnail.setImageResource(R.drawable.icon_open_camera);
			holder.imgThumbnail.setTag("camera");
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String SDState = Environment.getExternalStorageState();
					if (SDState.equals(Environment.MEDIA_MOUNTED)) {
						Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						((SystemAlbumPickerActivity)context).startActivityForResult(intent, SystemAlbumPickerActivity.requestCode_CAMERA);
					} else {
						Toast.makeText(context, "内存卡不存在", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			return convertView;
		}

		final SystemAlbumImageObjectVO imgObj = (SystemAlbumImageObjectVO) this.getItem(image_position);
		if (holder.imgThumbnail.getLayoutParams().height != mItemHeight) {
			if (mImageViewLayoutParams != null) {
				holder.imgThumbnail.setLayoutParams(mImageViewLayoutParams);
			}
		}

		imgObj.position = image_position;
		setImageView(imgObj, holder.imgThumbnail, imgObj.data);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra(SystemAlbumPickerActivity.key_singlePath, imgObj.data);
				((SystemAlbumPickerActivity)context).setResult(SystemAlbumPickerActivity.resultCode_SINGLE_PATH, data);
				((SystemAlbumPickerActivity)context).finish();

			}
		});
		return convertView;
	}
	@Override
	public int getCount() {
		return this.mList == null ? 1 : this.mList.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return this.mList == null ? null : this.mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setImageView(SystemAlbumImageObjectVO position, ImageView imageView, String imageUrl) {
		imageView.setTag(position);
		this.setImageView(position, imageView, imageUrl, mImageLoadListener);
	}

	public void setImageView(SystemAlbumImageObjectVO position, ImageView imageView, String imageUrl, OnImageLoadListener imageLoadListener) {
		imageLoadListener.setmListView(mListView);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		if (imageUrl == null) {
			imageView.setImageResource(imageLoadListener.defaultImageId);
			return;
		}

		Drawable d = syncImageLoader.getLocalImage(imageUrl);
		if (d != null) {
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setImageDrawable(d);
		} else {
			imageView.setImageResource(imageLoadListener.defaultImageId);
			syncImageLoader.loadImage(position, imageUrl, imageLoadListener);
		}
	}



	public class Holder {
		public ImageView imgThumbnail;
	}

	public void setItemHeight(int columnWidth) {
		if (mItemHeight == columnWidth) {
			return;
		}
		mItemHeight = columnWidth;
		if (mImageViewLayoutParams == null) {
			mImageViewLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, columnWidth);
		}
		notifyDataSetChanged();
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//				Log.d("scroll", "SCROLL_STATE_FLING" + "惯性");
//				loadImage();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//				Log.d("scroll", "SCROLL_STATE_IDLE" + "停止");
				loadImage();
				Runtime.getRuntime().gc();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//				Log.d("scroll", "SCROLL_STATE_TOUCH_SCROLL" + "触摸滚动");
				loadImage();
				break;
			default:
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		}
	};
	public void loadImage() {
		int start = mListView.getFirstVisiblePosition();
		int end = mListView.getLastVisiblePosition();
		if (end >= getCount()) {
			end = getCount() - 1;
		}
		syncImageLoader.setLoadLimit(start, end);
		syncImageLoader.unlock();
	}
	
	public static class OnImageLoadListener implements SystemAlbumPickerActivity_SyncPhotoLoader.OnImageLoadListener {
		AbsListView mListView;
		int defaultImageId;
		int defaultImageViewId;
		public OnImageLoadListener(AbsListView mListView, int defaultImageId, int defaultImageViewId) {
			this.mListView = mListView;
			this.defaultImageId = defaultImageId;
			this.defaultImageViewId = defaultImageViewId;
		}
		public OnImageLoadListener(int defaultImageId, int defaultImageViewId) {
			this.defaultImageId = defaultImageId;
			this.defaultImageViewId = defaultImageViewId;
		}
		public void setmListView(AbsListView mListView) {
			this.mListView = mListView;
		}
		@Override
		public void onImageLoad(SystemAlbumImageObjectVO t, Drawable drawable) {
			View view = this.mListView.findViewWithTag(t);
			// Log.w("onError",
			// "position:--->"+t+"  drawable"+drawable+"  view"+view);
			if (view != null && drawable != null) {
				ImageView iv = (ImageView) view.findViewById(this.defaultImageViewId);
				if (iv != null) {
					iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
					final TransitionDrawable td = new TransitionDrawable(
							new Drawable[] {
									new ColorDrawable(
											android.R.color.transparent),
									drawable });
					iv.setImageDrawable(td);
					td.startTransition(200);
				}
			}
		}

		@Override
		public void onError(SystemAlbumImageObjectVO t) {
			View view = this.mListView.findViewWithTag(t);
			// Log.w("onError", "position:--->"+t+"  view"+view);
			if (view != null) {
				ImageView iv = (ImageView) view.findViewById(this.defaultImageViewId);
				if (iv != null) {
					iv.setImageResource(this.defaultImageId);
				}
			}
		}
	}
}
