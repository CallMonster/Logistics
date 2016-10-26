package com.tj.opensrc.selectphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author kevin
 * com.xcos.unoptimize.PhotoThumbnailUtils;
 * 这里使用了 未优化 包里的 PhotoThumbnailUtils 类.
 *
 */
public class SystemAlbumPickerActivity_SyncPhotoLoader {

	private final String TAG="SyncPhotoLoader";
	private Object lock = new Object();
	/**
	 * 是否允许下载
	 */
	private boolean mAllowLoad = true;
	/**
	 * 是否第一次下载
	 */
	private boolean firstLoad = true;
	/**
	 * 开始下载的限制数量
	 */
	private int mStartLoadLimit = 0;
	/**
	 * 停止下载的限制数量
	 */
	private int mStopLoadLimit = 0;

	/**
     *
     */
	final Handler handler = new Handler();

	BlockingQueue<Runnable> queue;

	ThreadPoolExecutor executor;

	/**
	 * 图片缓存
	 */
	private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	static String imageFilePath = "";

	public SystemAlbumPickerActivity_SyncPhotoLoader() {
		queue = new LinkedBlockingQueue<Runnable>() {
			/**
		 *
		 */
			private static final long serialVersionUID = 8199562390839021539L;

			@Override
			public boolean offer(Runnable e) {
				if (contains(e)) {
					return true;
				}
				return super.offer(e);
			}

		};
		executor = new ThreadPoolExecutor(2, 100, 60, TimeUnit.SECONDS, queue);
	}

	public void clearAndStop() {
		if (queue != null) {
			queue.clear();
		}
		if (imageCache != null) {
			Iterator<SoftReference<Drawable>> bitmapCache = imageCache.values()
					.iterator();
			while (bitmapCache.hasNext()) {
				Drawable d = bitmapCache.next().get();
				if (d != null) {
					d.setCallback(null);
					d = null;
				}
			}
			imageCache.clear();
		}
		mAllowLoad = false;
	}

	public interface OnImageLoadListener {
		public void onImageLoad(SystemAlbumImageObjectVO t, Drawable drawable);

		public void onError(SystemAlbumImageObjectVO t);
	}

	public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {
		if (startLoadLimit > stopLoadLimit) {
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}

	public void restore() {
		mAllowLoad = true;
		firstLoad = true;
	}

	public void lock() {
		mAllowLoad = false;
		firstLoad = false;
	}

	public void unlock() {
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void loadImage(SystemAlbumImageObjectVO t, String imageUrl, OnImageLoadListener listener) {
		executor.execute(new ImageRunnable(imageUrl, t, listener));

		// Log.d("executor------>",
		// "getActiveCount--->" + executor.getActiveCount());
		// Log.d("executor------>",
		// "getCompletedTaskCount--->" + executor.getCompletedTaskCount());
		// Log.d("executor------>", "getTaskCount--->" +
		// executor.getTaskCount());
		// new Thread(new ImageRunnable(imageUrl, t, listener)).start();

	}

	public Drawable getLocalImage(String url) {
		Drawable d = null;
		if (imageCache.containsKey(url)) {
			SoftReference<Drawable> softReference = imageCache.get(url);
			d = softReference.get();
		}
		return d;
	}

	public static String getImageFilePath(String url, boolean md5) {
		return url;
	}

	private void loadImage(final String mImageUrl, final SystemAlbumImageObjectVO mt,
			final OnImageLoadListener mListener) {
		if (imageCache.containsKey(mImageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(mImageUrl);
			final Drawable d = softReference.get();
			if (d != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (mAllowLoad) {
							mListener.onImageLoad(mt, d);
						}
					}
				});
				return;
			}
		}
		if (mAllowLoad) {
			try {
				final Drawable d = loadImagefromUrl(mt);
				if (d != null) {
					imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
					if (!mAllowLoad) {
						synchronized (lock) {
							try {
								lock.wait();
							} catch (InterruptedException e) {
								Log.e(TAG, "", e);
							}
						}
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							mListener.onImageLoad(mt, d);
						}
					});
				}
			} catch (Exception e) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mListener.onError(mt);
					}
				});
				Log.e(TAG, "", e);
			}

		}
	}
	/**
	 * 查找sd卡图片
	 * @param imgObj
	 * @return
	 * @throws IOException
	 */
	private static Drawable loadImagefromUrl(SystemAlbumImageObjectVO imgObj)
			throws IOException {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			File file = getImageFile(imgObj);
			String path = file.getAbsolutePath();
			String oldPath = imgObj.data;
			if (file.exists()) {

				Bitmap bitmap = null;
				try {
					int mw = 300;
					int mh = 300;
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					// 获取这个图片的宽和高，注意此处的bitmap为null
					bitmap = BitmapFactory.decodeFile(path, options);
					options.inJustDecodeBounds = false;
					// 计算缩放比
					int h = options.outHeight;
					int w = options.outWidth;
					// Log.d("---------","压缩前数值:height:[" + h + "]" + "width:["
					// + w + "]");
					int beWidth = w / mw;
					int beHeight = h / mh;
					int be = 1;
					if (beWidth < beHeight) {
						be = beWidth;
					} else {
						be = beHeight;
					}
					if (be <= 0) {
						be = 1;
					}
					int sss = PhotoThumbnailUtils.computeSampleSize(options,
							mw, mw * mh);

					options.inSampleSize = sss / 2;

					options.inPreferredConfig = Bitmap.Config.RGB_565;

					bitmap = BitmapFactory.decodeFile(path, options);
					//
					// Matrix matrix = PhotoThumbnailUtils
					// .getMatrixOrientation(path);

					Bitmap bit = PhotoThumbnailUtils.extractThumbnail(bitmap,
							mw, mh, PhotoThumbnailUtils.OPTIONS_RECYCLE_INPUT);
					bit = PhotoThumbnailUtils.orientationBitMap(oldPath, bit);

					return new BitmapDrawable(bit);

				} catch (OutOfMemoryError oom) {
				}
				return new BitmapDrawable(bitmap);
			}
		}
		return null;
	}

	public static File getImageFile(SystemAlbumImageObjectVO imgObj) {
		File file = null;
		if (imgObj.thumbnail_data == null
				|| imgObj.thumbnail_data.length() == 0) {
			file = new File(imgObj.data);
		} else {
			file = new File(imgObj.thumbnail_data);
		}
		return file;
	}

	public class ImageRunnable implements Runnable {
		String mImageUrl = null;
		SystemAlbumImageObjectVO mt;
		OnImageLoadListener mListener = null;

		public ImageRunnable(String mImageUrl, SystemAlbumImageObjectVO mt, OnImageLoadListener mListener) {
			this.mImageUrl = mImageUrl;
			this.mt = mt;
			this.mListener = mListener;
		}

		@Override
		public void run() {
			if (!mAllowLoad) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						Log.e(TAG, "", e);
					}
				}
			}
			// loadImage(mImageUrl, mt, mListener);

			if (mAllowLoad && firstLoad) {
				loadImage(mImageUrl, mt, mListener);
			}
			//
			if (mAllowLoad && mt.position <= mStopLoadLimit
					&& mt.position >= mStartLoadLimit) {
				loadImage(mImageUrl, mt, mListener);
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((mImageUrl == null) ? 0 : mImageUrl.hashCode());
			result = prime * result + ((mt == null) ? 0 : mt.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ImageRunnable other = (ImageRunnable) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mImageUrl == null) {
				if (other.mImageUrl != null)
					return false;
			} else if (!mImageUrl.equals(other.mImageUrl))
				return false;
			if (mt == null) {
				if (other.mt != null)
					return false;
			} else if (!mt.equals(other.mt))
				return false;
			return true;
		}

		private SystemAlbumPickerActivity_SyncPhotoLoader getOuterType() {
			return SystemAlbumPickerActivity_SyncPhotoLoader.this;
		}

		@Override
		public String toString() {
			return "ImageRunnable [mImageUrl=" + mImageUrl + ", mt=" + mt
					+ ", mListener=" + mListener + "]";
		}

	}

}
